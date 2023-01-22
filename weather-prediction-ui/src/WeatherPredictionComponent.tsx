import React, { FunctionComponent, useState } from "react";
import CitySelector from './CitySelector';

import "./index.scss";
import WeatherReport from './WeatherReport';

const WeatherPredictionComponent: FunctionComponent = () => {

  const [city, setCity] = useState<string | null>(null);

  return (
    <div className="mt-10 text-3xl mx-auto max-w-6xl">
      <CitySelector setCity={setCity} />
      {city &&
        <WeatherReport city={city} />
      }
    </div>
  )
};
export default WeatherPredictionComponent;
