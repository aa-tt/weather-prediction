import express from 'express';
import weatherService from '../services/weatherService.js';

const router = express.Router();

router.get('/', async (req, res) => {
  try {
    const weatherData = await weatherService.getWeatherData();
    res.json(weatherData);
  } catch (error) {
    res.status(500).send(error.message);
  }
});

export default router;