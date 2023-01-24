export type Report = {
  alerts: Array<string>;
  mains: Array<string>;
  temp_min?: number;
  temp_max?: number;
}

export type DayAndReport = {
  day: string;
  report: Report;
}