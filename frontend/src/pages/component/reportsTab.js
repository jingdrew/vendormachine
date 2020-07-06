import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { fetchTransactions, reportSliceSelector } from '../slices/reportSlice';
import {
  Container,
  TextField,
  TableContainer,
  Table,
  TableRow,
  withStyles,
  TableBody,
  Paper,
  TableCell,
  TableHead,
} from '@material-ui/core';
import styles from './admin.module.css';

const StyledTableCell = withStyles((theme) => ({
  head: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  body: {
    fontSize: 14,
  },
}))(TableCell);

const StyledTableRow = withStyles((theme) => ({
  root: {
    '&:nth-of-type(odd)': {
      backgroundColor: theme.palette.action.hover,
    },
  },
}))(TableRow);

const ReportsTab = ({ data, token }) => {
  const dispatch = useDispatch();
  const reportSelector = useSelector(reportSliceSelector);
  const [transactions, setTransactions] = useState([]);
  const [selectedDate, setSelectedDate] = useState();

  useEffect(() => {
    if (data.machine) {
      const date = formatDate(new Date());
      setSelectedDate(date);
      dispatch(fetchTransactions(data.machine.id, date, token));
    }
  }, [dispatch, data.machine, token]);

  useEffect(() => {
    if (reportSelector.status === 'success') {
      setTransactions(reportSelector.reports);
    }
  }, [reportSelector]);

  const formatDate = (date) => {
    var dd = String(date.getDate()).padStart(2, '0');
    var mm = String(date.getMonth() + 1).padStart(2, '0');
    var yyyy = date.getFullYear();
    const result = yyyy + '-' + mm + '-' + dd;
    return result;
  };

  const handleChangeDate = (e) => {
    if (data.machine) {
      dispatch(fetchTransactions(data.machine.id, e.target.value, token));
    }
  };

  const renderDatePicker = () => {
    return (
      <TextField
        id='date'
        label='Birthday'
        type='date'
        defaultValue={selectedDate}
        onChange={handleChangeDate}
        InputLabelProps={{
          shrink: true,
        }}
      />
    );
  };

  const calculateCosts = () => {
    let cost = 0;
    transactions.forEach((t) => {
      cost += parseFloat(t.productCost);
    });
    return cost;
  };

  const calculatePrice = () => {
    let price = 0;
    transactions.forEach((t) => {
      price += parseFloat(t.productSellPrice);
    });
    return price;
  };

  const renderPage = () => {
    if (data.machine) {
      return (
        <TableContainer component={Paper}>
          <Table aria-label='customized table'>
            <TableHead>
              <TableRow>
                <StyledTableCell>Date</StyledTableCell>
                <StyledTableCell align='center'>Vendor Machine</StyledTableCell>
                <StyledTableCell align='center'>Product Name</StyledTableCell>
                <StyledTableCell align='center'>Product Cost </StyledTableCell>
                <StyledTableCell align='center'>Product Price</StyledTableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {transactions.map((t) => (
                <StyledTableRow key={t.id}>
                  <StyledTableCell component='th' scope='row'>
                    {t.created}
                  </StyledTableCell>
                  <StyledTableCell align='center'>
                    {t.machineName}
                  </StyledTableCell>
                  <StyledTableCell align='center'>
                    {t.productName}
                  </StyledTableCell>
                  <StyledTableCell align='center'>
                    ${t.productCost}
                  </StyledTableCell>
                  <StyledTableCell align='center'>
                    ${t.productSellPrice}
                  </StyledTableCell>
                </StyledTableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      );
    }
  };

  return (
    <Container>
      <div className={styles.top}>
        <div>{renderDatePicker()}</div>
        <div className={styles.sumary}>
          <h3>Total Costs:</h3> ${calculateCosts()}
          <h3>Total Sells:</h3> ${calculatePrice()}
          <h3>Total Profit:</h3> ${calculatePrice() - calculateCosts()}
        </div>
      </div>
      <div>{renderPage()}</div>
    </Container>
  );
};

export default ReportsTab;
