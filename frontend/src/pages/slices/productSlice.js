import { createSlice } from '@reduxjs/toolkit';
import axios from 'axios';

const API = 'http://localhost:8080/api/v1/admin';

const ProductSlice = createSlice({
  name: 'products',
  initialState: {
    status: 'idle',
    products: null,
    error: null,
  },
  reducers: {
    setRequesting: (state) => {
      state.status = 'loading';
      state.error = null;
    },
    setRequestError: (state, action) => {
      state.status = 'error';
      state.error = action.payload;
    },
    setRequestSuccess: (state, action) => {
      state.error = null;
      state.status = 'success';
      state.products = action.payload;
    },
  },
});

export const {
  setRequesting,
  setRequestError,
  setRequestSuccess,
} = ProductSlice.actions;

export const productSliceSelector = (state) => state.products;

export default ProductSlice.reducer;

export const fetchProducts = (token) => (dispatch) => {
  const url = API + '/product/list';
  const headers = { Authorization: token };

  dispatch(setRequesting());
  axios
    .get(url, { headers: headers })
    .then((res) => {
      dispatch(setRequestSuccess(res.data));
    })
    .catch((error) => {
      let msg = 'Something went wrong.';
      if (error.response) {
        msg = error.response.data.message;
      }
      dispatch(setRequestError(msg));
    });
};

export const saveProducts = (data, token) => (dispatch) => {
  const url = API + '/product';
  const headers = { Authorization: token };

  dispatch(setRequesting());
  axios
    .post(url, data, { headers: headers })
    .then((res) => {
      dispatch(setRequestSuccess(res.data));
    })
    .catch((error) => {
      let msg = 'Something went wrong.';
      if (error.response) {
        msg = error.response.data.message;
      }
      dispatch(setRequestError(msg));
    });
};
