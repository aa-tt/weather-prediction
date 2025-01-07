import { Injectable } from '@nestjs/common';

@Injectable()
export class WeatherService {
  getWeather() {
    // Your logic to get weather data
    return { temperature: 25, condition: 'Sunny' };
  }
}