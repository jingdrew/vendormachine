import { createSlice } from '@reduxjs/toolkit';
import axios from 'axios';

const API = 'http://localhost:8080/api/v1/machine';

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
      state.error = null;
      state.status = 'success';
      state.result = action.payload;
    },
  },
});

export const {
  setRequesting,
  setRequestError,
  setRequestSuccess,
} = TransactionSlice.actions;

export const transactionSelector = (state) => state.transaction;

export default TransactionSlice.reducer;

export const addCredit = (machineId, amount) => (dispatch) => {
  const url = API + '/credit/add?machine=' + machineId + '&amount=' + amount;
  dispatch(setRequesting());
  axios
    .get(url)
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

export const withdrawCredits = (machineId) => (dispatch) => {
  const url = API + '/credit/withdraw?machine=' + machineId;
  dispatch(setRequesting());
  axios
    .get(url)
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
