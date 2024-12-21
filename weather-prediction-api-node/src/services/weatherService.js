import axios from 'axios';
import weatherRepository from '../repositories/weatherRepository.js';

const getWeatherData = async () => {
  const apiKey = process.env.WEATHER_API_KEY;
  const response = await axios.get(`http://api.openweathermap.org/data/2.5/weather?q=London&appid=${apiKey}`);
  await weatherRepository.cacheWeatherData(response.data);
  return response.data;
};

export default {
  getWeatherData
};