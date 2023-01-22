let baseUrl = "http://localhost:8080";
let api = "/api/v1/forecast";

export const setbaseUrl = (productionUrl: string) => {
  baseUrl = productionUrl;
}

export const setApi = (versionApi: string) => {
  api = versionApi;
}

export async function getWeatherReport(city: string): Promise<Array<String>> {

  const response = await fetch(`${baseUrl}/cities/${city}`);
  const data: string[] = await response.json();
  return data;
}