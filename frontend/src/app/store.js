import { configureStore } from '@reduxjs/toolkit';
import homeReducer from '../pages/slices/homeSlice';

export default configureStore({
  reducer: {
    home: homeReducer,
  },
});
