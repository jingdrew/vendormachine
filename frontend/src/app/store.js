import { configureStore } from '@reduxjs/toolkit';
import homeReducer from '../pages/slices/homeSlice';
import machineReducer from '../pages/slices/machineSlice';
import transactionReducer from '../pages/slices/transactionSlice';
import adminReducer from '../pages/slices/adminSlice';
import productReducer from '../pages/slices/productSlice';
import reportReducer from '../pages/slices/reportSlice';

export default configureStore({
  reducer: {
    home: homeReducer,
    machine: machineReducer,
    transaction: transactionReducer,
    admin: adminReducer,
    products: productReducer,
    reports: reportReducer,
  },
});
