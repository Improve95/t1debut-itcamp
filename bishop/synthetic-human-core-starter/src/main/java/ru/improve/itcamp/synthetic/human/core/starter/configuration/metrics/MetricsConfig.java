package ru.improve.itcamp.synthetic.human.core.starter.configuration.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.threadPool.QueueThreadPool;

@Configuration
public class MetricsConfig {

    @Bean
    public MeterBinder meterBinder(QueueThreadPool queueThreadPool, MetricsNameConfig metricsNameConfig) {
        return meterRegistry -> {
            Gauge.builder(
                        metricsNameConfig.getQueueTaskNumberMeterName(),
                        queueThreadPool,
                        QueueThreadPool::getQueueSize
                    )
                    .register(meterRegistry);

            Counter.builder(metricsNameConfig.getCounterRequestByNameCounter())
                    .tags("author", "user")
                    .register(meterRegistry);
        };
    }
}
