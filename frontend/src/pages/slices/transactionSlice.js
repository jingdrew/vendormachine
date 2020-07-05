import { createSlice } from '@reduxjs/toolkit';
import axios from 'axios';

const API = 'http://localhost:8080/api/v1/machine/transaction';

const TransactionSlice = createSlice({
  name: 'transaction',
  initialState: {
    status: 'idle',
    result: null,
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
      const result = action.payload;
      if (result.transactionHistory.status === 'FAIL') {
        state.error = result.transactionHistory.reason;
        state.status = 'error';
      } else {
        state.error = null;
        state.status = 'success';
      }
      state.result = result;
    },
  },
});

export const {
  setRequesting,
  setRequestError,
  setRequestSuccess,
} = TransactionSlice.actions;

export const transactionSliceSelector = (state) => state.transaction;

export default TransactionSlice.reducer;

export const buyProduct = (machineId, productSlotId, payment) => (dispatch) => {
  const url = API + '/buy?machine=' + machineId + '&slot=' + productSlotId;
  dispatch(setRequesting);
  console.log(url);

  axios
    .post(url, payment)
    .then((res) => {
      console.log(res.data);

      dispatch(setRequestSuccess(res.data));
    })
    .catch((error) => {
      console.log(error);

      let msg = 'Something went wrong.';
      if (error.response) {
        msg = error.response.data.message;
      }
      dispatch(setRequestError(msg));
    });
};
