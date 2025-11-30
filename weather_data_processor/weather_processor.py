import datetime
import json
import uuid
from confluent_kafka import Consumer, KafkaException
from astrapy.client import DataAPIClient

# Kafka consumer configuration
kafka_config = {
    'bootstrap.servers': 'pkc-619z3.us-east1.gcp.confluent.cloud:9092',
    'security.protocol': 'SASL_SSL',
    'sasl.mechanisms': 'PLAIN',
    'sasl.username': '3GWHA4CPJAOKCUXQ',
    'sasl.password': '5a8B1YX5tEr+buSye0pPoapbVdaK/qEvL2BcvuTRwGv2nRbT6vLJ+P13wkijxjm+',
    'group.id': f'weather_group_{uuid.uuid4()}',
    'auto.offset.reset': 'earliest'
}

# Create Astra client
client = DataAPIClient(
    "astraCS-token"
)
db = client.get_database_by_api_endpoint(
    "https://5ebf24fb-d5db-4ab7-a0ac-9f8ad5c0d50d-us-east1.apps.astra.datastax.com",
    keyspace="weather_ns"
)

print(f"Connected to Astra DB: {db.list_collection_names()}")

# Create Kafka consumer
try:
    consumer = Consumer(kafka_config)
    consumer.subscribe(['weather_data_topic_0'])
except KafkaException as e:
    print(f"Failed to create Kafka consumer: {e}")
    exit(1)

def process_weather_data(weather_data):
    weather_alerts = []
    if weather_data['wind']['speed'] > 10:
        weather_alerts.append("It’s too windy, watch out!")
    if weather_data['main']['temp'] > 40:
        weather_alerts.append("Use sunscreen lotion")
    if 'rain' in weather_data:
        weather_alerts.append("Carry Umbrella")
    
    weather_report = {
        "alerts": weather_alerts,
        "mains": [weather['main'] for weather in weather_data['weather']],
        "temp_min": weather_data['main']['temp_min'],
        "temp_max": weather_data['main']['temp_max'],
        "dt": datetime.datetime.fromtimestamp(weather_data['dt']).isoformat(),
    }
    return weather_report

def store_weather_report(city, report):
    document_id = str(uuid.uuid4())
    timestamp = datetime.datetime.fromisoformat(report['dt']).astimezone().isoformat()
    
    document = {
        # "_id": document_id,
        "city": city,
        "timestamp": timestamp,
        "temp_min": report['temp_min'],
        "temp_max": report['temp_max'],
        "alerts": report['alerts'],
        "mains": report['mains'],
    }
    collection = db.get_collection("weather_report")
    collection.insert_one(document)

try:
    while True:
        msg = consumer.poll(1.0)
        if msg is not None and msg.error() is None:
            print(f"Consumed message: {msg.key().decode('utf-8')}")
            print(f"Consumed message: {msg.value().decode('utf-8')}")
            weather_data = json.loads(msg.value().decode('utf-8'))
            city = msg.key().decode('utf-8')
            weather_report = process_weather_data(weather_data)
            store_weather_report(city, weather_report)
        elif msg is not None and msg.error():
            print(f"Consumer error: {msg.error()}")
except KeyboardInterrupt:
    pass
finally:
    consumer.close()