import React from 'react';
import { FunctionComponent, useEffect, useState } from 'react';
import { getWeatherReport } from './api/call';

type Props = {
  city: string;
};

const WeatherReport: FunctionComponent<Props> = (props: Props) => {

  const { city } = props;

  const [temperature, setTemperature] = useState<Partial<Report> | null>(null);
  const [reports, setReports] = useState<string[]>([]);

  useEffect(() => {
    getWeatherReport(city).then(
      (data: Report) => {
        setReports(data.alerts);
        setTemperature(data);
      }
    ).catch(
      console.error
    )
  }, [city]);

  return (
    <>
      {reports && reports.map(report => (
        <div key={report} className='mt-5 bg-white dark:bg-slate-800 rounded-lg px-6 py-8 ring-1 ring-slate-900/5 shadow-xl'>
          <div className='mt-2 text-sm'>
            <p className='text-slate-500 dark:text-slate-400 bold'>
              Temperature:
            </p>
            <span className='text-slate-500 dark:text-slate-400'>
              min: {temperature?.temp_min}
            </span>
            <span className='ml-5 text-slate-500 dark:text-slate-400'>
              max: {temperature?.temp_max}
            </span>
          </div>
          <p className='text-slate-500 dark:text-slate-400 mt-2 text-5xl'>
            {report}
          </p>
        </div>
      ))}
    </>
  )
}

export default WeatherReport;