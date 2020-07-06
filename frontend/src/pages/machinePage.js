import React, { useState, useEffect } from 'react';
import {
  Container,
  makeStyles,
  Dialog,
  DialogTitle,
  DialogActions,
  DialogContent,
  Button,
  TextField,
} from '@material-ui/core';
import { fetchMachine, machineSelector } from './slices/machineSlice';
import { login, loginSliceSelector } from './slices/loginSlice';
import { useDispatch, useSelector } from 'react-redux';
import { useLocation, useHistory } from 'react-router-dom';
import DisplayPanel from './component/displayPanel';
import ControlPanel from './component/controlPanel';
import ResultDialog from './component/resultDialog';

const useStyles = makeStyles({
  container: {
    marginTop: '50px !important',
    display: 'flex',
  },

  left_panel: {
    width: '70%',
  },

  right_panel: {
    width: '30%',
  },
  error: {
    color: 'red',
  },
});

const MachinePage = () => {
  const location = useLocation();
  const history = useHistory();
  const dispatch = useDispatch();
  const loginSelector = useSelector(loginSliceSelector);
  const selector = useSelector(machineSelector);
  const styles = useStyles();
  const [error, setError] = useState('');
  const [password, setPassword] = useState('');
  const [data, setData] = useState({
    machine: null,
    selectedSlot: null,
    showDialog: false,
    results: null,
    action: null,
    showPassDialog: false,
  });

  useEffect(() => {
    if (location.state) {
      dispatch(fetchMachine(location.state.id));
    } else {
      history.push('/');
    }
  }, [location, dispatch, history]);

  useEffect(() => {
    if (selector.status === 'success') {
      setData({
        machine: selector.machine,
        selectedSlot: null,
        showDialog: false,
        results: null,
        action: null,
        showPassDialog: false,
      });
    }
  }, [selector]);

  useEffect(() => {
    if (loginSelector.status === 'success') {
      history.push('/admin', {
        id: selector.machine.id,
        token: loginSelector.token,
      });
    } else if (loginSelector.status === 'error') {
      setError(loginSelector.error);
    }
  }, [loginSelector]);

  const handleCloseDialog = () => {
    let newData = { ...data };
    newData.showPassDialog = false;
    setData(newData);
  };

  const handleLogin = () => {
    if (password.length > 0) {
      //history.push('/admin', { id: selector.machine.id, key: 'casd' });
      dispatch(login(data.machine.model, password));
    } else {
      setError('Password is epmty.');
    }
  };

  const passwordDialog = () => {
    return (
      <Dialog onClose={handleCloseDialog} open={data.showPassDialog}>
        <DialogTitle id='customized-dialog-title' onClose={handleCloseDialog}>
          Administration Panel
        </DialogTitle>
        <DialogContent dividers>
          <div>
            <TextField
              id='outlined-password-input'
              label='Password'
              type='password'
              autoComplete='current-password'
              variant='outlined'
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <p className={styles.error}>{error}</p>
          </div>
        </DialogContent>
        <DialogActions>
          <Button autoFocus onClick={handleCloseDialog} color='primary'>
            Close
          </Button>
          <Button autoFocus onClick={handleLogin} color='primary'>
            Login
          </Button>
        </DialogActions>
      </Dialog>
    );
  };

  return (
    <div>
      {passwordDialog()}
      <ResultDialog data={data} setData={setData} />
      <Container>
        <div className={styles.container}>
          <div className={styles.left_panel}>
            <DisplayPanel data={data} setData={setData} />
          </div>
          <div className={styles.right_panel}>
            <ControlPanel data={data} setData={setData} />
          </div>
        </div>
      </Container>
    </div>
  );
};

export default MachinePage;
