from pyflink.datastream import StreamExecutionEnvironment
from pyflink.datastream.functions import MapFunction

class WeatherDataProcessFunction(MapFunction):
    def map(self, value):
        # Execute the weather_processor.py script
        import subprocess
        subprocess.run(["/usr/local/Caskroom/miniconda/base/envs/weather_env/bin/python", "weather_processor.py"])
        return value

env = StreamExecutionEnvironment.get_execution_environment()
data_stream = env.from_collection([1])
data_stream.map(WeatherDataProcessFunction()).print()

env.execute("Weather Data Processor Job")