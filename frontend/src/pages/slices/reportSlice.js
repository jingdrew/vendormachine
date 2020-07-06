import { createSlice } from '@reduxjs/toolkit';
import axios from 'axios';

const API = 'http://localhost:8080/api/v1/admin';

const ReportSlice = createSlice({
  name: 'reports',
  initialState: {
    status: 'idle',
    reports: [],
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
      state.reports = action.payload;
    },
  },
});

export const {
  setRequesting,
  setRequestError,
  setRequestSuccess,
} = ReportSlice.actions;

export const reportSliceSelector = (state) => state.reports;

export default ReportSlice.reducer;

export const fetchTransactions = (id, date) => (dispatch) => {
  const url = API + '/transaction/list?machine=' + id + '&date=' + date;
  console.log(url);

  dispatch(setRequesting());
  axios
    .get(url)
    .then((res) => {
      dispatch(setRequestSuccess(res.data));
    })
    .catch((error) => {
      let msg = 'Server is offline.';
      if (error.response) {
        msg = error.response.data.message;
      }
      dispatch(setRequestError(msg));
    });
};
