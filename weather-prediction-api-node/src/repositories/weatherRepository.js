import redis from 'redis';

const redisClient = redis.createClient({ url: process.env.REDIS_URL });

const cacheWeatherData = async (data) => {
  await redisClient.set('weatherData', JSON.stringify(data));
};

export default {
  cacheWeatherData
};