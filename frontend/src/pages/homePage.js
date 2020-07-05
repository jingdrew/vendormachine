import React, { useEffect, useState } from 'react';
import {
  Container,
  Grid,
  Card,
  makeStyles,
  CardActionArea,
  CardContent,
  Typography,
} from '@material-ui/core';
import { fetchMachines, homeSlector } from './slices/homeSlice';
import { useSelector, useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';

const useStyles = makeStyles({
  grid: {
    marginTop: 150,
    justifyContent: 'center',
  },
  root: {
    height: 250,
  },
});

const HomePage = () => {
  const styles = useStyles();
  const dispatch = useDispatch();
  const history = useHistory();
  const selector = useSelector(homeSlector);
  const [machines, setMachines] = useState([]);

  useEffect(() => {
    dispatch(fetchMachines());
  }, [dispatch]);

  useEffect(() => {
    if (selector.status === 'success') {
      setMachines(selector.machines);
    }
  }, [selector, dispatch]);

  return (
    <Container>
      <Grid container spacing={3} className={styles.grid}>
        {machines.map((m, index) => (
          <Grid item xs={3} key={index}>
            <Card elevation={5}>
              <CardActionArea
                className={styles.root}
                onClick={() =>
                  history.push({
                    pathname: '/machine',
                    state: { id: m.id },
                  })
                }
              >
                <CardContent>
                  <Typography gutterBottom variant='h5' component='h2'>
                    {m.model}
                  </Typography>
                  <Typography
                    variant='body2'
                    color='textSecondary'
                    component='p'
                  >
                    {m.description}
                  </Typography>
                </CardContent>
              </CardActionArea>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Container>
  );
};

export default HomePage;
