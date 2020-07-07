import { createSlice } from '@reduxjs/toolkit';
import axios from 'axios';

const URL = 'http://localhost:8080/api/v1/auth/authenticate';

const LoginSlice = createSlice({
  name: 'login',
  initialState: {
    status: 'idle',
    token: null,
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
      state.token = 'Bearer ' + action.payload;
    },
  },
});

export const {
  setRequesting,
  setRequestError,
  setRequestSuccess,
} = LoginSlice.actions;

export const loginSliceSelector = (state) => state.login;

export default LoginSlice.reducer;

export const login = (username, password) => (dispatch) => {
  const data = {
    username: username,
    password: password,
  };
  dispatch(setRequesting());
  axios
    .post(URL, data)
    .then((res) => {
      dispatch(setRequestSuccess(res.data.token));
    })
    .catch((error) => {
      let msg = 'Something went wrong.';
      if (error.response) {
        msg = error.response.data.message;
      }
      dispatch(setRequestError(msg));
    });
};
