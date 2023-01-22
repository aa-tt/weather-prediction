import React from 'react';
import { FunctionComponent, useEffect, useState } from 'react';
import { getWeatherReport } from './api/call';

type Props = {
  city: string;
};

const WeatherReport: FunctionComponent<Props> = (props: Props) => {

  const { city } = props;

  const [reports, setReports] = useState<string[]>([]);

  useEffect(() => {
    getWeatherReport(city).then(
      (data) => {
        console.log(data)
        setReports(data as string[])
      }
    ).catch(
      console.error
    )
  }, [city]);

  return (
    <>
      {reports && reports.map(report => (
        <div key={report} className='mt-5 bg-white dark:bg-slate-800 rounded-lg px-6 py-8 ring-1 ring-slate-900/5 shadow-xl'>
          <p className='text-slate-500 dark:text-slate-400 mt-2 text-5xl'>
            {report}
          </p>
        </div>
      ))}
    </>
  )
}

export default WeatherReport;