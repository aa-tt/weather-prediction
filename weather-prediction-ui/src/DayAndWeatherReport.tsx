import React from 'react';
import { FunctionComponent, useEffect, useState } from 'react';
import { getWeatherForecastByDays, getWeatherReport } from './api/call';
import { DayAndReport, Report } from './model/Report';

type Props = {
  city: string;
};

const DayAndWeatherReport: FunctionComponent<Props> = (props: Props) => {

  const { city } = props;

  // const [temperature, setTemperature] = useState<Partial<Report> | null>(null);
  const [reports, setReports] = useState<DayAndReport[]>([]);

  useEffect(() => {
    getWeatherForecastByDays(city).then(
      (data: DayAndReport[]) => {
        setReports(data);
      }
    ).catch(
      console.error
    )
  }, [city]);

  return (
    <>
      {reports && reports.map(dayReport => (
        <div key={dayReport.day} className='mt-5 bg-white dark:bg-slate-800 rounded-lg px-6 py-8 ring-1 ring-slate-900/5 shadow-xl'>
          <div className='mt-2 text-sm'>
            <p className='text-slate-500 dark:text-slate-400 bold'>
              {dayReport.day}
            </p>
            <p className='text-slate-500 dark:text-slate-400 bold'>
              Temperature:
            </p>
            <span className='text-slate-500 dark:text-slate-400'>
              min: {dayReport.report.temp_min}
            </span>
            <span className='ml-5 text-slate-500 dark:text-slate-400'>
              max: {dayReport.report.temp_max}
            </span>
          </div>
          <p className='text-slate-500 dark:text-slate-400 mt-2 text-5xl'>
            {dayReport.report.mains.map(m => (
              <span key={m}>{m}</span>
            ))}
          </p>
        </div>
      ))}
    </>
  )
}

export default DayAndWeatherReport;