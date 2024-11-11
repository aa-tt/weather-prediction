import { DayAndReport, Report } from '../model/Report';

// let baseUrl = "http://localhost:8080";
let baseUrl = "/api";
let api = "/api/v1/forecast";

export const setbaseUrl = (productionUrl: string) => {
  baseUrl = productionUrl;
}

export const setApi = (versionApi: string) => {
  api = versionApi;
}

export async function getWeatherReport(city: string): Promise<Report> {

  const response = await fetch(`${baseUrl}/cities/${city}/forecast`);
  const data: Report = await response.json();
  return data;
}

export async function getWeatherForecastByDays(city: string, days: number = 3): Promise<DayAndReport[]> {

  const response = await fetch(`${baseUrl}/cities/${city}/days/${days}/forecast`);
  const data: DayAndReport[] = await response.json();
  return data;
}