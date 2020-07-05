import { createSlice } from '@reduxjs/toolkit';
import axios from 'axios';

const API = 'http://localhost:8080/api/v1/machine';

const HomeSlice = createSlice({
  name: 'home',
  initialState: {
    status: 'idle',
    machines: [],
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
      state.machines = action.payload;
    },
  },
});

export const {
  setRequesting,
  setRequestError,
  setRequestSuccess,
} = HomeSlice.actions;

export const homeSlector = (state) => state.home;

export default HomeSlice.reducer;

export const fetchMachines = () => (dispatch) => {
  const url = API + '/list';
  dispatch(setRequesting());
  console.log('asdasd');

  axios
    .get(url)
    .then((res) => {
      console.log(res);

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
