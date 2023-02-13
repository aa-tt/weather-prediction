import React, { FunctionComponent, useEffect, useState } from "react";
import CitySelector from './shared/CitySelector';
import { counterService } from './shared/CounterService';
import DayAndWeatherReport from './forecast/DayAndWeatherReport';

import WeatherReport from './daily/WeatherReport';

const WeatherPredictionComponent: FunctionComponent = () => {

  const [city, setCity] = useState<string | null>(null);
  const [messages, setMessages] = useState<string[]>([]);

  useEffect(() => {

    // subscribe to home component messages
    const subscription = counterService.onMessage().subscribe(message => {
      if (message) {
        // add message to local state if not empty
        setMessages(messages => [...messages, message]);
      } else {
        // clear messages when empty message received
        setMessages([]);
      }
    });

    // return unsubscribe method to execute when component unmounts
    return subscription.unsubscribe;

  }, []);

  return (
    <div className="mt-10 text-3xl mx-auto max-w-6xl">
      {messages.map(m => (
        <div className='flex flex-row text-sm' key={m}>
          <div>{m}</div>
        </div>
      ))
      }
      <CitySelector setCity={setCity} />
      <div className='mt-3'>
        <p className='text-gray-500 dark:text-gray-400 bold'>
          Forecast
        </p>
        <div className='flex'>
          {city &&
            <DayAndWeatherReport city={city} />
          }
        </div>
        <p className='text-gray-500 dark:text-gray-400 bold mt-2'>
          Weather Today
        </p>
        {city &&
          <WeatherReport city={city} />
        }
      </div>
    </div >
  )
};
export default WeatherPredictionComponent;
