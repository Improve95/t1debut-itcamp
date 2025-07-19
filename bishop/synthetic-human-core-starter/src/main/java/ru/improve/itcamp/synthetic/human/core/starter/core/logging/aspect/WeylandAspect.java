package ru.improve.itcamp.synthetic.human.core.starter.core.logging.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.improve.itcamp.synthetic.human.core.starter.api.exception.ServiceException;
import ru.improve.itcamp.synthetic.human.core.starter.core.logging.publisher.MethodLoggingPublisher;

import java.time.Instant;

import static ru.improve.itcamp.synthetic.human.core.starter.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class WeylandAspect {

//    private final String publisherType = "consoleMethodLoggingPublisher";

    private final MethodLoggingPublisher loggingPublisher;

//    public WeylandAspect(@Qualifier(publisherType) MethodLoggingPublisher loggingPublisher) {
//        this.loggingPublisher = loggingPublisher;
//    }

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

        loggingPublisher.publishLog(
                methodName,
                joinpoint.getArgs(),
                result,
                methodStartAt,
                Instant.now().toEpochMilli()
        );

        if (result instanceof Throwable) {
            log.info("aroundMethodWithWeylandAnnotation throw with {}", result);
            throw new ServiceException(INTERNAL_SERVER_ERROR, (Throwable) result);
        }

        log.info("aroundMethodWithWeylandAnnotation end with {}", result);
        return result;
    }
}
