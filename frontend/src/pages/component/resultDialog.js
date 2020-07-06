import React, { useState, useEffect } from 'react';
import {
  Dialog,
  Table,
  TableBody,
  TableRow,
  TableCell,
  Button,
  DialogTitle,
  DialogContent,
  Paper,
  DialogActions,
  TableContainer,
  makeStyles,
  CardMedia,
  TableHead,
} from '@material-ui/core';

const useStyles = makeStyles({
  image: {
    height: '270px',
  },
});

const ResultDialog = ({ data, setData }) => {
  const [show, setShow] = useState(false);
  const styles = useStyles();

  useEffect(() => {
    console.log(data);

    setShow(data.showDialog);
  }, [data.showDialog]);

  const handleCloseDialog = () => {
    let newData = { ...data };
    newData.showDialog = false;
    newData.result = null;
    newData.action = null;
    newData.selectedSlot = null;
    setData(newData);
  };

  const renderCardPayResults = () => {
    if (data.result) {
      var val = Math.floor(1000 + Math.random() * 9999);
      var receipe = Math.floor(10000 + Math.random() * 99999999);
      return (
        <Dialog onClose={handleCloseDialog} open={show}>
          <DialogTitle id='customized-dialog-title' onClose={handleCloseDialog}>
            Order # {receipe}
          </DialogTitle>
          <DialogContent dividers>
            <div>
              <CardMedia
                className={styles.image}
                image={data.result.transactionHistory.product.image}
              />
              <TableContainer component={Paper}>
                <Table aria-label='simple table'>
                  <TableBody>
                    <TableRow>
                      <TableCell component='th'>Pay With</TableCell>
                      <TableCell align='right'>
                        {' *****************' + val}
                      </TableCell>
                    </TableRow>

                    <TableRow>
                      <TableCell component='th'>Item</TableCell>
                      <TableCell align='right'>
                        {data.result.transactionHistory.productName}
                      </TableCell>
                    </TableRow>

                    <TableRow>
                      <TableCell component='th'>Price</TableCell>
                      <TableCell align='right'>
                        {data.result.transactionHistory.productSellPrice}
                      </TableCell>
                    </TableRow>
                  </TableBody>
                </Table>
              </TableContainer>
              <p>Thanks !!</p>
            </div>
          </DialogContent>
          <DialogActions>
            <Button autoFocus onClick={handleCloseDialog} color='primary'>
              Close
            </Button>
          </DialogActions>
        </Dialog>
      );
    }
  };

  const renderCashPayResults = () => {
    if (data.result) {
      var receipe = Math.floor(10000 + Math.random() * 99999999);
      return (
        <Dialog onClose={handleCloseDialog} open={show}>
          <DialogTitle id='customized-dialog-title' onClose={handleCloseDialog}>
            Order # {receipe}
          </DialogTitle>
          <DialogContent dividers>
            <div>
              <CardMedia
                className={styles.image}
                image={data.result.transactionHistory.product.image}
              />
              <TableContainer component={Paper}>
                <Table aria-label='simple table'>
                  <TableBody>
                    <TableRow>
                      <TableCell component='th'>Item</TableCell>
                      <TableCell align='right'>
                        {data.result.transactionHistory.productName}
                      </TableCell>
                    </TableRow>

                    <TableRow>
                      <TableCell component='th'>Pay With</TableCell>
                      <TableCell align='right'>
                        ${data.result.payWith}
                      </TableCell>
                    </TableRow>

                    <TableRow>
                      <TableCell component='th'>Price</TableCell>
                      <TableCell align='right'>
                        ${data.result.transactionHistory.product.price}
                      </TableCell>
                    </TableRow>

                    <TableRow>
                      <TableCell component='th'>Change</TableCell>
                      <TableCell align='right'>${data.result.change}</TableCell>
                    </TableRow>
                    {data.result.moneyStacksList.map((m, index) => (
                      <div>
                        {m.qty > 0 ? (
                          <TableRow key={index}>
                            <TableCell component='th'>{m.qty}</TableCell>
                            <TableCell align='right'>
                              {m.currency.valueName}
                            </TableCell>
                          </TableRow>
                        ) : (
                          <div></div>
                        )}
                      </div>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
              <p>Thanks !!</p>
            </div>
          </DialogContent>
          <DialogActions>
            <Button autoFocus onClick={handleCloseDialog} color='primary'>
              Close
            </Button>
          </DialogActions>
        </Dialog>
      );
    }
  };

  const renderWithdrawResults = () => {
    if (data.result) {
      return (
        <Dialog onClose={handleCloseDialog} open={show}>
          <DialogTitle id='customized-dialog-title' onClose={handleCloseDialog}>
            Withdraw Results
          </DialogTitle>
          <DialogContent dividers>
            <div>
              <TableContainer component={Paper}>
                <Table>
                  <TableHead>
                    <TableRow>
                      <TableCell>Quantity</TableCell>
                      <TableCell>Value</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {data.result.map((r, index) => (
                      <TableRow key={index}>
                        <TableCell>{r.qty}</TableCell>
                        <TableCell>{r.currency.valueName}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </div>
          </DialogContent>
          <DialogActions>
            <Button autoFocus onClick={handleCloseDialog} color='primary'>
              Close
            </Button>
          </DialogActions>
        </Dialog>
      );
    }
  };

  const renderPage = () => {
    if (data.action === 'BUY') {
      if (data.result.transactionHistory.paymentMethod === 'CARD') {
        return renderCardPayResults();
      } else if (data.result.transactionHistory.paymentMethod === 'CASH') {
        return renderCashPayResults();
      }
    } else if (data.action === 'WITHDRAW') {
      return renderWithdrawResults();
    }
  };
  return <div>{renderPage()}</div>;
};

export default ResultDialog;
