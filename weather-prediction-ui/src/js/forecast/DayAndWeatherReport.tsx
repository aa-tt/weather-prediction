import React from 'react';
import { FunctionComponent, useEffect, useState } from 'react';
import { getWeatherForecastByDays } from '../../api/call';
import { DayAndReport } from '../../model/Report';

type Props = {
  city: string;
};

const DayAndWeatherReport: FunctionComponent<Props> = (props: Props) => {

  const { city } = props;
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // const [temperature, setTemperature] = useState<Partial<Report> | null>(null);
  const [reports, setReports] = useState<DayAndReport[]>([]);

  useEffect(() => {
    getWeatherForecastByDays(city).then(
      (data) => {
        console.log("---widget two data---");
        console.log(data);
        setReports(data);
      }
    ).catch(
      (error) => setError(error)
    ).finally(
      () => setLoading(false)
    )
  }, [city]);

  if (loading) {
    return <div>Loading Widget Two...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }
  return (
    <>
      {Array.isArray(reports) && reports.map(dayReport => (
        <div key={dayReport?.dt} className='mt-5 bg-white dark:bg-slate-800 rounded-lg px-6 py-8 ring-1 ring-slate-900/5 shadow-xl'>
          <div className='mt-2 text-sm'>
            <p className='text-slate-500 dark:text-slate-400 bold'>
              {dayReport?.dt}
            </p>
            <p className='text-slate-500 dark:text-slate-400 bold'>
              Temperature:
            </p>
            <span className='text-slate-500 dark:text-slate-400'>
              min: {dayReport?.temp_min}
            </span>
            <span className='ml-5 text-slate-500 dark:text-slate-400'>
              max: {dayReport?.temp_max}
            </span>
          </div>
          <p className='text-slate-500 dark:text-slate-400 mt-2 text-5xl'>
            {dayReport?.alerts?.map(m => (
              <span key={m}>{m}</span>
            ))}
          </p>
        </div>
      ))}
    </>
  )
}

export default DayAndWeatherReport;