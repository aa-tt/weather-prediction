import React from "react";
import ReactDOM from "react-dom";

import "./index.scss";
import WeatherPredictionComponent from './js/WeatherPredictionComponent';

const App = () => (
  <div className="mt-10 text-3xl mx-auto max-w-6xl">
    Test
    <WeatherPredictionComponent
    ></WeatherPredictionComponent>
  </div>
);
ReactDOM.render(<App />, document.getElementById("app"));
