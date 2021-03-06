import React from 'react';
import './App.css';
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom';
import HomePage from './pages/homePage';
import MachinePage from './pages/machinePage';
import AdminPage from './pages/adminPage';

const App = () => {
  return (
    <div className='App'>
      <BrowserRouter>
        <Switch>
          <Route exact path='/'>
            <HomePage />
          </Route>
          <Route exact path='/machine'>
            <MachinePage />
          </Route>
          <Route exact path='/admin'>
            <AdminPage />
          </Route>
          <Redirect to='/' />
        </Switch>
      </BrowserRouter>
    </div>
  );
};

export default App;
