import React, { useState, useEffect } from 'react';
import {
  Grid,
  Card,
  CardActionArea,
  CardMedia,
  CardContent,
  makeStyles,
} from '@material-ui/core';
const useStyles = makeStyles({
  image: {
    height: '270px',
  },
  productText: {
    fontSize: '18px',
  },
  sold_out: {
    color: 'red',
    fontSize: '18px',
  },
  selected: {
    backgroundColor: 'green',
  },
});

const DisplayPanel = ({ data, setData }) => {
  const styles = useStyles();
  const [machine, setMachine] = useState(null);
  const [selected, setSelected] = useState(null);

  useEffect(() => {
    setMachine(data.machine);
  }, [data.machine]);

  useEffect(() => {
    setSelected(data.selectedSlot);
  }, [data.selectedSlot]);

  const handleProductSelect = (slot) => {
    if (selected !== slot) {
      setData({
        machine: machine,
        selectedSlot: slot,
      });
    }
  };

  const handleSelectedStyle = (slot) => {
    if (slot.qty > 0) {
      if (selected === slot) {
        return styles.selected;
      }
    }
  };

  const render = () => {
    if (machine) {
      return (
        <Grid container spacing={3}>
          {machine.productSlotList.map((s, index) => (
            <Grid item xs={3} key={index}>
              <Card elevation={15} className={handleSelectedStyle(s)}>
                <CardActionArea onClick={() => handleProductSelect(s, index)}>
                  <CardMedia className={styles.image} image={s.product.image} />
                  <CardContent className={styles.productText}>
                    <p>
                      {s.product.name} ${s.product.price}
                    </p>

                    {s.qty > 0 ? (
                      <div>{s.qty} Units</div>
                    ) : (
                      <div className={styles.sold_out}>Sold out</div>
                    )}
                  </CardContent>
                </CardActionArea>
              </Card>
            </Grid>
          ))}
        </Grid>
      );
    }
  };

  return <div>{render()}</div>;
};

export default DisplayPanel;
