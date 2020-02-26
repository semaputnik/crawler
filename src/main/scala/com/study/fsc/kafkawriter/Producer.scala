package com.study.fsc.kafkawriter

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class Producer {
  val topic = "crawler_answer"
  val server = "localhost:9092"
  val keySerializer = "org.apache.kafka.common.serialization.LongSerializer"
  val valueSerializer = "org.apache.kafka.common.serialization.StringSerializer"

  def write(key: Long, value: String): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", server)
    props.put("key.serializer", keySerializer)
    props.put("value.serializer", valueSerializer)

    val producer = new KafkaProducer[Long, String](props)
    val record = new ProducerRecord[Long, String](topic, key, value)

    producer.send(record)
    producer.close()
  }
}
