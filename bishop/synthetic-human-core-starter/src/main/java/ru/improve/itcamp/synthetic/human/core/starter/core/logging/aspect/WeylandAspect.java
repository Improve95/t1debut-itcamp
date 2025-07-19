package ru.improve.itcamp.synthetic.human.core.starter.core.logging.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.improve.itcamp.synthetic.human.core.starter.api.exception.ServiceException;
import ru.improve.itcamp.synthetic.human.core.starter.core.kafka.object.KafkaWeylandMessage;
import ru.improve.itcamp.synthetic.human.core.starter.core.kafka.producer.WeylandProducer;

import java.time.Instant;

import static ru.improve.itcamp.synthetic.human.core.starter.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@Slf4j
@Component
@Aspect
public class WeylandAspect {

    private final WeylandProducer weylandProducer;

    @Pointcut("@annotation(ru.improve.itcamp.synthetic.human.core.starter.core.logging.WeylandWatchingYou)")
    public void anyMethodWithWeylandAnnotation() {}

    @Around(value = "anyMethodWithWeylandAnnotation()")
    public Object aroundAnyMethodWithWeylandAnnotation(ProceedingJoinPoint joinpoint) {
        String[] methodNameSplit = joinpoint.getSignature().toString().split("\\.");
        String methodName = methodNameSplit[methodNameSplit.length - 1];
        log.info(
                "aroundMethodWithWeylandAnnotation start with: {} -||- {}",
                methodName,
                joinpoint.getArgs()
        );

        long methodStartAt = Instant.now().toEpochMilli();

        Object result;
        try {
            result = joinpoint.proceed();
        } catch (Throwable ex) {
            result = ex;
        }

        weylandProducer.sendActionLog(
                KafkaWeylandMessage.builder()
                        .methodName(methodName)
                        .parameters(joinpoint.getArgs())
                        .result(result)
                        .methodStartAt(methodStartAt)
                        .methodEndAt(Instant.now().toEpochMilli())
                        .build()
        );

        if (result instanceof Throwable) {
            log.info("aroundMethodWithWeylandAnnotation throw with {}", result);
            throw new ServiceException(INTERNAL_SERVER_ERROR, (Throwable) result);
        }

        log.info("aroundMethodWithWeylandAnnotation end with {}", result);
        return result;
    }
}
