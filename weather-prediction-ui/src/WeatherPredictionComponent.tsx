import React, { FunctionComponent, useState } from "react";
import CitySelector from './CitySelector';
import DayAndWeatherReport from './DayAndWeatherReport';

import "./index.scss";
import WeatherReport from './WeatherReport';

const WeatherPredictionComponent: FunctionComponent = () => {

  const [city, setCity] = useState<string | null>(null);

  return (
    <div className="mt-10 text-3xl mx-auto max-w-6xl">
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
    </div>
  )
};
export default WeatherPredictionComponent;
