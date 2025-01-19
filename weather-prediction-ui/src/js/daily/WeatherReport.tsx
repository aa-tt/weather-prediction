import React from 'react';
import { FunctionComponent, useEffect, useState } from 'react';
import { getWeatherReport, getWeatherReportAlertPerdictions } from '../../api/call';
import { Report } from '../../model/Report';

type Props = {
  city: string;
};

const WeatherReport: FunctionComponent<Props> = (props: Props) => {

  const { city } = props;
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [alerts, setAlerts] = useState([]);
  const [report, setReport] = useState<Partial<Report> | null>(null);

  useEffect(() => {
    getWeatherReport(city).then(
      (data: Report) => {
        console.log("---widget one data---");
        console.log(data);
        setReport(data);
      }
    ).catch(
      (error) => setError(error)
    ).finally(
      () => setLoading(false)
    )
  }, [city]);

  useEffect(() => {
    const eventSource = getWeatherReportAlertPerdictions(city);
    eventSource.onmessage = (event) => {
      const newAlerts = JSON.parse(event.data);
      setAlerts(newAlerts);
    };

    eventSource.onerror = (error) => {
      console.error('EventSource failed:', error);
      eventSource.close();
    };

    return () => {
      eventSource.close();
    };
  }, [city]);

  if (loading) {
    return <div>Loading Widget One...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <>
      {report && (
        <div className='mt-5 bg-white dark:bg-slate-800 rounded-lg px-6 py-8 ring-1 ring-slate-900/5 shadow-xl'>
          <div className='mt-2 text-sm'>
            <p className='text-slate-500 dark:text-slate-400 bold'>
              Temperature:
            </p>
            <span className='text-slate-500 dark:text-slate-400'>
              min: {report?.temp_min}
            </span>
            <span className='ml-5 text-slate-500 dark:text-slate-400'>
              max: {report?.temp_max}
            </span>
          </div>
          <p className='text-slate-500 dark:text-slate-400 mt-2 text-5xl'>
            {alerts.join(', ')}
          </p>
        </div>
      )}
    </>
  )
}

export default WeatherReport;