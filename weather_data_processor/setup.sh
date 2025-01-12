mkdir weather_data_processor
cd weather_data_processor
conda create --name weather_env python=3.12
conda activate weather_env
touch environment.yml
conda env update --file environment.yml
touch weather_processor.py
# assume flink is installed in ./flink-1.20.0
touch flink_job.py
./flink-1.20.0/bin/flink run -py flink_job.py