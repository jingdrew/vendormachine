import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  Grid,
  Card,
  CardContent,
  CardMedia,
  TextField,
} from '@material-ui/core';
import {
  fetchProducts,
  saveProducts,
  productSliceSelector,
} from '../slices/productSlice';
import styles from './admin.module.css';

const ProductInventoryTab = ({ token }) => {
  const dispatch = useDispatch();
  const productSelector = useSelector(productSliceSelector);
  const [products, setProducts] = useState([]);

  useEffect(() => {
    if (token) {
      dispatch(fetchProducts(token));
    }
  }, [dispatch, token]);

  useEffect(() => {
    if (productSelector.status === 'success') {
      setProducts(productSelector.products);
    }
  }, [productSelector]);

  const handleChange = (e, index) => {
    const id = e.target.id;
    const value = e.target.value;
    if (value && value <= 100 && value >= 0) {
      let newProducts = [...products];
      if (id === 'cost') {
        newProducts[index] = { ...newProducts[index], cost: value };
      } else {
        newProducts[index] = { ...newProducts[index], price: value };
      }
      setProducts(newProducts);
      dispatch(saveProducts(newProducts, token));
    }
  };

  const renderProducts = () => {
    return (
      <div className={styles.container}>
        <Grid container spacing={3}>
          {products.map((p, index) => (
            <Grid item xs={3} key={index}>
              <Card elevation={15}>
                <CardMedia className={styles.image} image={p.image} />
                <CardContent>
                  <h3>{p.name}</h3>
                  <TextField
                    id='cost'
                    label='Cost Price (USD)'
                    type='number'
                    variant='filled'
                    value={p.cost}
                    onChange={(e) => handleChange(e, index)}
                  />
                  <br></br>
                  <TextField
                    id='sell'
                    label='Sell Price (USD)'
                    type='number'
                    variant='filled'
                    value={p.price}
                    onChange={(e) => handleChange(e, index)}
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
  };

  return <div>{renderProducts()}</div>;
};

export default ProductInventoryTab;
