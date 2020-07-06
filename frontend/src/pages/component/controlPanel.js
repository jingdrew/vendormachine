import React, { useState, useEffect } from 'react';
import { Button } from '@material-ui/core';
import {
  buyProduct,
  setRequesting,
  transactionSliceSelector,
} from '../slices/transactionSlice';
import { useSelector, useDispatch } from 'react-redux';
import styles from './controlPanel.module.css';

const ControlPanel = ({ data, setData }) => {
  const dispatch = useDispatch();
  const transactionSelector = useSelector(transactionSliceSelector);
  const [machine, setMachine] = useState(null);
  const [totalCredit, setTotalCredit] = useState(0);
  const [message, setMessage] = useState('Welcome :)');
  const [cardInserted, setCardInserted] = useState(false);
  const [showingMsg, setShowingMsg] = useState(false);
  const [creditStack, setCreditStack] = useState([]);
  const [handlingPayment, setHandlingPayment] = useState(false);

  useEffect(() => {
    setMachine(data.machine);
  }, [data.machine]);

  useEffect(() => {
    if (transactionSelector.status === 'loading') {
      setHandlingPayment(true);
      if (cardInserted) {
        setMessage('Processing Payment...');
        showTempMessage('Verifying Credit/Debit Card...');
      }
    } else if (transactionSelector.status === 'error') {
      setHandlingPayment(false);
      if (cardInserted) {
        showTempMessage('Credit/Debit Card Rejected, Payment Not Processed');
        setCardInserted(false);
      } else {
        showTempMessage('Payment Not Processed, Please Try Again');
      }
    } else if (transactionSelector.status === 'success') {
      setHandlingPayment(false);
      showTempMessage('Transaction Succesful. Thank You!!!');
      let newData = { ...data };
      newData.machine = transactionSelector.result.transactionHistory.machine;
      newData.action = 'BUY';
      newData.result = transactionSelector.result;
      newData.selectedSlot = null;
      newData.showDialog = true;
      setData(newData);
      setTotalCredit(0);
      setCreditStack([]);
    }
  }, [transactionSelector]);

  useEffect(() => {
    if (totalCredit === 0) {
      setMessage('Welcome :)');
    }
    if (data.selectedSlot) {
      setMessage('Please Insert Payment');
      let credit = 0;
      creditStack.forEach((m) => {
        credit += m.qty * m.currency.value;
      });
      setTotalCredit(credit);
      if (credit >= data.selectedSlot.product.price) {
        setHandlingPayment(true);
        const payment = {
          paymentMethod: 'CASH',
          moneyStacks: [...creditStack],
        };
        dispatch(buyProduct(machine.id, data.selectedSlot.id, payment));
      }
    }
  }, [data.selectedSlot, creditStack]);

  const showTempMessage = (newMsg) => {
    if (!showingMsg) {
      setShowingMsg(true);
      const prevMessage = message;
      setMessage(newMsg);
      setTimeout(() => {
        setMessage(prevMessage);
        setShowingMsg(false);
      }, 2000);
    }
  };

  const handleButtonClick = (val) => {
    if (val === 'admin') {
      let newData = { ...data };
      newData.showPassDialog = true;
      setData(newData);
    } else {
      if (cardInserted) {
        setCardInserted(false);
      } else {
        if (totalCredit > 0) {
          let newData = { ...data };
          newData.showDialog = true;
          newData.action = 'WITHDRAW';
          newData.result = creditStack;
          setData(newData);
          setCreditStack([]);
        }
      }
    }
  };
  const handleAddPayment = (val, name) => {
    if (handlingPayment) return;
    if (data.selectedSlot) {
      if (val === 'CARD') {
        if (totalCredit > 0) {
          showTempMessage(
            'Please withdraw your credit and reinsert your credit/debit card'
          );
        } else {
          setCardInserted(true);
          setHandlingPayment(true);
          dispatch(setRequesting());
          setTimeout(() => {
            const payment = {
              paymentMethod: 'CARD',
              moneyStacks: [],
            };
            dispatch(buyProduct(machine.id, data.selectedSlot.id, payment));
          }, 2000);
        }
      } else {
        const index = creditStack.findIndex((c) => c.currency.value === val);
        if (index === -1) {
          setCreditStack([
            ...creditStack,
            { currency: { value: val, valueName: name }, qty: 1 },
          ]);
        } else {
          let stacks = [...creditStack];
          stacks[index] = {
            ...stacks[index],
            qty: stacks[index].qty + 1,
          };
          setCreditStack(stacks);
        }
      }
    } else {
      showTempMessage('Please select your item you want to buy first');
    }
  };

  const render = () => {
    if (machine) {
      return (
        <div>
          <h2>{machine.model}</h2>
          <div className={styles.display_screen}>
            <p className={styles.display_credits}>
              CREDITS: ${totalCredit.toFixed(2)}
            </p>
            <p className={styles.display_message}>{message}</p>
          </div>
          <div className={styles.payment_container}>
            <hr />
            <div>
              <Button
                className={styles.small_button}
                onClick={() => handleAddPayment('2', '$2 Dollars')}
              >
                $2 BILL
              </Button>
              <Button
                className={styles.small_button}
                onClick={() => handleAddPayment('1', '$1 Dollar')}
              >
                $1 BILL
              </Button>
              <Button
                className={styles.small_button}
                onClick={() => handleAddPayment('0.5', '50 Cents')}
              >
                50 CENTS
              </Button>
              <Button
                className={styles.small_button}
                onClick={() => handleAddPayment('0.25', '25 Cents')}
              >
                25 CENTS
              </Button>
              <Button
                className={styles.small_button}
                onClick={() => handleAddPayment('0.10', '10 Cents')}
              >
                10 CENTS
              </Button>
              <Button
                className={styles.small_button}
                onClick={() => handleAddPayment('0.05', '5 Cents')}
              >
                5 CENTS
              </Button>
            </div>

            <hr />
            {machine.acceptedPaymentMethod === 'ALL' ? (
              <div>
                <Button
                  className={styles.card_button}
                  onClick={() => handleAddPayment('CARD')}
                >
                  CREDIT/DEBIT CARD
                </Button>
                <hr />
              </div>
            ) : (
              <div></div>
            )}
            <Button
              className={styles.withdraw_button}
              onClick={() => handleButtonClick('withdraw')}
            >
              WITHDRAW
            </Button>
            <hr />
            <Button
              className={styles.admin_button}
              onClick={() => handleButtonClick('admin')}
            >
              ADMIN
            </Button>
          </div>
        </div>
      );
    }
  };

  return <div>{render()}</div>;
};

export default ControlPanel;
