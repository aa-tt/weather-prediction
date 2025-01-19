export type Report = {
  alerts: Array<string>;
  mains: Array<string>;
  temp_min?: number;
  temp_max?: number;
}

export type DayAndReport = {
  dt: string;
  alerts: Array<string>;
  mains: Array<string>;
  temp_min?: number;
  temp_max?: number;
}