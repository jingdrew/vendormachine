import React, { useEffect, useState } from 'react';
import { useLocation, useHistory } from 'react-router-dom';
import { fetchMachine, adminSliceSelector } from './slices/adminSlice';
import { useDispatch, useSelector } from 'react-redux';
import { Tab, AppBar, Container, Tabs } from '@material-ui/core';
import TabPanel from './component/tabPanel';
import ProductInventoryTab from './component/productInventoryTab';
import MoneyInventoryTab from './component/moneyInventoryTab';
import ProductTab from './component/productTab';
import ReportsTab from './component/reportsTab';

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

const AdminPage = () => {
  const location = useLocation();
  const history = useHistory();
  const dispatch = useDispatch();
  const [value, setValue] = useState(0);
  const adminSelector = useSelector(adminSliceSelector);
  const [token, setToken] = useState('');
  const [data, setData] = useState({
    machine: null,
  });

  useEffect(() => {
    if (location.state) {
      setToken(location.state.token);
      dispatch(fetchMachine(location.state.id, location.state.token));
    } else {
      history.push('/');
    }
  }, [location, dispatch, history]);

  useEffect(() => {
    if (adminSelector.status === 'success') {
      setData({
        machine: adminSelector.machine,
      });
    }
  }, [adminSelector]);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Container>
      <AppBar position='static'>
        <Tabs
          value={value}
          onChange={handleChange}
          aria-label='simple tabs example'
        >
          <Tab label='Product Inventory' {...a11yProps(0)} />
          <Tab label='Money Inventory' {...a11yProps(1)} />
          <Tab label='Products' {...a11yProps(2)} />
          <Tab label='Reports' {...a11yProps(3)} />
        </Tabs>
      </AppBar>
      <TabPanel value={value} index={0}>
        <ProductInventoryTab data={data} setData={setData} token={token} />
      </TabPanel>
      <TabPanel value={value} index={1}>
        <MoneyInventoryTab data={data} setData={setData} token={token} />
      </TabPanel>
      <TabPanel value={value} index={2}>
        <ProductTab token={token} />
      </TabPanel>
      <TabPanel value={value} index={3}>
        <ReportsTab data={data} token={token} />
      </TabPanel>
    </Container>
  );
};

export default AdminPage;
