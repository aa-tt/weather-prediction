/* Copyright 2020 Confluent Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * =============================================================================
 *
 * Consume messages from Confluent Cloud
 * Using the node-rdkafka client for Apache Kafka
 *
 * =============================================================================
 */

const { Kafka } = require('kafkajs')

const kafka = new Kafka({
  clientId: 'my-wapp',
  brokers: ['localhost:9092'],
})

const consumer = kafka.consumer({ groupId: 'nodejs-consumer-group-1' })

async function consumerEzample() {

  await consumer.connect()
  await consumer.subscribe({ topic: 'quickstart', fromBeginning: true })

  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      console.log({
        value: message.value.toString(),
      })
    },
  })
}

consumerEzample()
  .catch((err) => {
    console.error(`Something went wrong:\n${err}`);
    process.exit(1);
  });
