import { createSlice } from '@reduxjs/toolkit';
import axios from 'axios';

const API = 'http://localhost:8080/api/v1/machine';

const MachineSlice = createSlice({
  name: 'machine',
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
} = MachineSlice.actions;

export const machineSelector = (state) => state.machine;

export default MachineSlice.reducer;

export const fetchMachine = (machineId) => (dispatch) => {
  const url = API + '/' + machineId;
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
