import { configureStore } from '@reduxjs/toolkit';
import homeReducer from '../pages/slices/homeSlice';
import machineReducer from '../pages/slices/machineSlice';
import transactionReducer from '../pages/slices/transactionSlice';

export default configureStore({
  reducer: {
    home: homeReducer,
    machine: machineReducer,
    transaction: transactionReducer,
  },
});
