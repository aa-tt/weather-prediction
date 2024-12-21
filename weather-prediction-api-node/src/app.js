import express from 'express';
import redis from 'redis';
import consul from 'consul';
import dotenv from 'dotenv';
import weatherController from './controllers/weatherController.js';

dotenv.config();

const app = express();
const port = 8080;

const redisClient = redis.createClient({ url: process.env.REDIS_URL });
const consulClient = consul({ host: process.env.CONSUL_HOST, port: 8500 });

app.use(express.json());

// CORS setup
app.use((req, res, next) => {
  res.header('Access-Control-Allow-Origin', process.env.ALLOW_ORIGINS);
  res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
  next();
});

// Import routes
app.use('/weather', weatherController);

app.listen(port, () => {
  console.log(`Weather Prediction API running at http://localhost:${port}`);
});