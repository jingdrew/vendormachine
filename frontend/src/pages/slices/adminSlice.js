import { createSlice } from '@reduxjs/toolkit';
import axios from 'axios';

const API = 'http://localhost:8080/api/v1/admin';

const AdminSlice = createSlice({
  name: 'admin',
  initialState: {
    status: 'idle',
    machine: null,
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
      state.machine = action.payload;
    },
  },
});

export const {
  setRequesting,
  setRequestError,
  setRequestSuccess,
} = AdminSlice.actions;

export const adminSliceSelector = (state) => state.admin;

export default AdminSlice.reducer;

export const fetchMachine = (machineId, token) => (dispatch) => {
  const url = API + '/machine/' + machineId;
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

export const saveProductSlots = (id, data, token) => (dispatch) => {
  const url = API + '/machine/pslot?machine=' + id;
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

export const saveMoneySlots = (id, data, token) => (dispatch) => {
  const url = API + '/machine/mslot?machine=' + id;
  const headers = { Authorization: token };
  console.log(token);

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
