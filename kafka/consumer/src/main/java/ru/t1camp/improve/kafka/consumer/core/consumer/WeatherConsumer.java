package ru.t1camp.improve.kafka.consumer.core.consumer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;
import ru.t1camp.improve.kafka.consumer.core.object.kafka.WeatherMessageKafka;

import java.time.Duration;

@RequiredArgsConstructor
@Slf4j
@Component
public class WeatherConsumer {

    private final KafkaConsumer<String, WeatherMessageKafka> weatherKafkaConsumer;

    private Thread consumerThread;

    @PostConstruct
    public void startWeatherConsumer() {
        consumerThread = new Thread(this::listenWeatherEventTopic);
        consumerThread.start();
    }

    @PreDestroy
    public void stopWeatherConsumer() {
        consumerThread.interrupt();
        weatherKafkaConsumer.close();
    }

    private void listenWeatherEventTopic() {
        while (true) {
            ConsumerRecords<String, WeatherMessageKafka> records = weatherKafkaConsumer.poll(Duration.ofMillis(100));
            for (var record : records) {
                WeatherMessageKafka weatherMessageKafka = record.value();
                log.info("receive message {}", weatherMessageKafka);
                //todo anything logic
            }
        }
    }
}
