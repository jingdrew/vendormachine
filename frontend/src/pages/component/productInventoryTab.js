import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import {
  Grid,
  Card,
  CardContent,
  CardMedia,
  TextField,
} from '@material-ui/core';
import { saveProductSlots } from '../slices/adminSlice';
import styles from './admin.module.css';

const ProductInventoryTab = ({ data, setData }) => {
  const dispatch = useDispatch();
  const [machine, setMachine] = useState(null);

  useEffect(() => {
    if (data.machine) {
      setMachine(data.machine);
    }
  }, [data.machine]);

  const handleChangeUnit = (e, index) => {
    const value = e.target.value;
    if (value && value <= 100 && value >= 0) {
      let newSlots = [...machine.productSlotList];
      newSlots[index] = { ...newSlots[index], qty: value };
      setData({
        ...data,
        machine: { ...machine, productSlotList: newSlots },
      });
      dispatch(saveProductSlots(machine.id, newSlots));
    }
  };

  const renderPage = () => {
    if (machine) {
      return (
        <div className={styles.container}>
          <Grid container spacing={3}>
            {machine.productSlotList.map((s, index) => (
              <Grid item xs={3} key={index}>
                <Card elevation={15}>
                  <CardMedia className={styles.image} image={s.product.image} />
                  <CardContent>
                    <h3>{s.product.name}</h3>
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

export default ProductInventoryTab;
