import React, { useState, useEffect } from 'react';
import { Container, makeStyles } from '@material-ui/core';
import { fetchMachine, machineSelector } from './slices/machineSlice';
import { useDispatch, useSelector } from 'react-redux';
import { useLocation, useHistory } from 'react-router-dom';
import DisplayPanel from './component/displayPanel';
import ControlPanel from './component/controlPanel';

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
});

const MachinePage = () => {
  const location = useLocation();
  const history = useHistory();
  const dispatch = useDispatch();
  const selector = useSelector(machineSelector);
  const styles = useStyles();
  const [data, setData] = useState({
    machine: null,
    selectedSlot: null,
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
      });
    }
  }, [selector]);

  return (
    <div>
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
