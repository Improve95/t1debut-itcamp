package ru.t1debut.itcamp.consent.core.kafka.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

import static ru.t1debut.itcamp.consent.util.ExceptionUtil.createExceptionMessage;

@Slf4j
@Component
public class ConsumerExceptionHandler implements CommonErrorHandler {

    @Override
    public void handleOtherException(
            Exception thrownException,
            Consumer<?, ?> consumer,
            MessageListenerContainer container,
            boolean batchListener
    ) {
        handleException(thrownException);
    }

    @Override
    public boolean handleOne(
            Exception thrownException,
            ConsumerRecord<?, ?> record,
            Consumer<?, ?> consumer,
            MessageListenerContainer container
    ) {
        handleException(thrownException);
        return true;
    }

    private void handleException(Exception ex) {
        log.error("handle kafka listener exception: {}", createExceptionMessage(ex, null));
    }
}
