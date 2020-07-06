import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { Grid, Card, CardContent, TextField } from '@material-ui/core';
import { saveMoneySlots } from '../slices/adminSlice';
import styles from './admin.module.css';

const MoneyInventoryTab = ({ data, setData, token }) => {
  const dispatch = useDispatch();
  const [machine, setMachine] = useState(null);

  useEffect(() => {
    if (data.machine) {
      setMachine(data.machine);
    }
  }, [data.machine]);

  const handleChangeUnit = (e, index) => {
    const value = e.target.value;
    if (value && value <= 300 && value >= 0) {
      let newSlots = [...machine.moneySlotList];
      newSlots[index] = { ...newSlots[index], qty: value };
      setData({
        ...data,
        machine: { ...machine, moneySlotList: newSlots },
      });
      dispatch(saveMoneySlots(machine.id, newSlots, token));
    }
  };

  const renderPage = () => {
    if (machine) {
      return (
        <div className={styles.container}>
          <Grid container spacing={3}>
            {machine.moneySlotList.map((s, index) => (
              <Grid item xs={3} key={index}>
                <Card elevation={15}>
                  <CardContent>
                    <h3>{s.currency.valueName}</h3>
                    <TextField
                      id='filled-number'
                      label='Units'
                      type='number'
                      variant='filled'
                      value={s.qty}
                      onChange={(e) => handleChangeUnit(e, index)}
                    />
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
          <br />
          <br />
        </div>
      );
    }
  };

  return <div>{renderPage()}</div>;
};

export default MoneyInventoryTab;
