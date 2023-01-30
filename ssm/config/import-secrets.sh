while ! curl -f -s http://$CONSUL_HTTP_ADDR/v1/status/leader | grep "[0-9]:[0-9]"; do
  sleep 5
done

consul kv put config/weather-prediction/open_weather_api.api_key @/consul/config/secret
