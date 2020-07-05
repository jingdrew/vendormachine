import React, { useState, useEffect } from 'react';
import { Button, makeStyles } from '@material-ui/core';
import {
  addCredit,
  withdrawCredits,
  transactionSelector,
} from '../slices/transactionSlice';
import {} from 'react-redux';
const useStyles = makeStyles({
  display_screen: {
    backgroundColor: '#204829',
    marginLeft: '25px',
    marginRight: '25px',
    height: '170px',
  },
  display_credits: {
    paddingTop: '25px',
    color: '#22b430',
    fontSize: '30px',
  },
  display_message: {
    color: '#22b480',
    fontSize: '20px',
    marginLeft: '25px',
    marginRight: '25px',
  },
  payment_container: {
    paddingLeft: '25px',
    paddingRight: '25px',
  },
  small_button: {
    margin: '10px !important',
    height: '100px',
    width: '100px',
    color: 'white !important',
    background: 'green !important',
  },
  card_button: {
    height: '50px',
    width: '100%',
    color: 'white !important',
    background: 'green !important',
  },
  withdraw_button: {
    height: '50px',
    width: '100%',
    color: 'white !important',
    background: 'blue !important',
  },
  admin_button: {
    height: '50px',
    width: '100%',
    color: 'white !important',
    background: 'red !important',
  },
});

const ControlPanel = (props) => {
  const [machine, setMachine] = useState(null);
  const styles = useStyles();
  const [credit, setCredit] = useState(0);
  const [message, setMessage] = useState('Welcome :)');

  useEffect(() => {
    if (props.machine) {
      console.log(props.machine);

      setMachine(props.machine);
    }
  }, [props]);

  const handleButtonClick = (val) => {};
  const handleAddCredit = (val) => {
    console.log(val);
  };

  const render = () => {
    if (machine) {
      return (
        <div>
          <h2>{machine.model}</h2>
          <div className={styles.display_screen}>
            <p className={styles.display_credits}>CREDITS: ${credit}</p>
            <p className={styles.display_message}>{message}</p>
          </div>
          <div className={styles.payment_container}>
            <hr />
            <div>
              <Button
                className={styles.small_button}
                onClick={() => handleAddCredit('2')}
              >
                $2 BILL
              </Button>
              <Button
                className={styles.small_button}
                onClick={() => handleAddCredit('1')}
              >
                $2 BILL
              </Button>
              <Button
                className={styles.small_button}
                onClick={() => handleAddCredit('0.5')}
              >
                $2 BILL
              </Button>
              <Button
                className={styles.small_button}
                onClick={() => handleAddCredit('0.25')}
              >
                $2 BILL
              </Button>
              <Button
                className={styles.small_button}
                onClick={() => handleAddCredit('0.10')}
              >
                $2 BILL
              </Button>
              <Button
                className={styles.small_button}
                onClick={() => handleAddCredit('0.05')}
              >
                $2 BILL
              </Button>
            </div>
            <hr />
            {machine.acceptedPaymentMethod === 'ALL' ? (
              <Button className={styles.card_button}>CREDIT/DEBIT CARD</Button>
            ) : (
              <div></div>
            )}
            <hr />
            <Button className={styles.withdraw_button}>WITHDRAW</Button>
            <hr />
            <Button className={styles.admin_button}>ADMIN</Button>
          </div>
        </div>
      );
    }
  };

  return <div>{render()}</div>;
};

export default ControlPanel;
