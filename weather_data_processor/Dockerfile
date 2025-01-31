# Use the official Python image from the Docker Hub
FROM python:3.10-slim

# Set the working directory in the container
WORKDIR /app

# Copy the requirements file into the container
COPY requirements.txt .

# Install the dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy the kafka-client.properties file into the container
COPY kafka-client.properties .

# Copy the rest of the application code into the container
COPY . .

# Set the entry point to run the weather_processor.py script
ENTRYPOINT ["python", "weather_processor.py"]