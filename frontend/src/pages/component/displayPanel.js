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
  available: {
    color: 'green',
    fontSize: '15px',
  },
  sold_out: {
    color: 'red',
    fontSize: '15px',
  },
});

const DisplayPanel = (props) => {
  const [machine, setMachine] = useState(null);
  const styles = useStyles();

  useEffect(() => {
    if (props.machine) {
      setMachine(props.machine);
    }
  }, [props]);

  const handleBuyProduct = () => {};

  const render = () => {
    if (machine) {
      return (
        <Grid container spacing={3}>
          {machine.productSlotList.map((s, index) => (
            <Grid item xs={3} key={index}>
              <Card elevation={15}>
                <CardActionArea onClick={() => handleBuyProduct(s)}>
                  <CardMedia className={styles.image} image={s.product.image} />
                  <CardContent>
                    {s.product.name} ${s.product.price}
                    {s.qty > 0 ? (
                      <p className={styles.available}>{s.qty} Units</p>
                    ) : (
                      <p className={styles.sold_out}>Sold out</p>
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
