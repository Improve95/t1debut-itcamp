package ru.improve.itcamp.synthetic.human.core.starter.core.metrics.aspect;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.improve.itcamp.synthetic.human.core.starter.configuration.metrics.MetricsNameConfig;
import ru.improve.itcamp.synthetic.human.core.starter.core.command.object.TaskInfo;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class MetricsLoggingAspect {

    private final MetricsNameConfig metricsNameConfig;

    private final MeterRegistry meterRegistry;

    @Pointcut("within(ru.improve.itcamp.synthetic.human.core.starter.core.command.executor.TaskExecutor+)")
    public void anyMethodWithInterfaceTaskExecutor(){}

    @Pointcut("execution(* executeTask(ru.improve.itcamp.synthetic.human.core.starter.core.command.object.TaskInfo))")
    public void anyMethodWithExecuteTaskName(){}

    @AfterReturning("anyMethodWithInterfaceTaskExecutor() && anyMethodWithExecuteTaskName()")
    public void afterReturningAnyMethodWhichExecuteCommand(JoinPoint joinPoint) {
        TaskInfo taskInfo = (TaskInfo) joinPoint.getArgs()[0];
        meterRegistry.counter(
                    metricsNameConfig.getCounterRequestByNameCounter(),
                    List.of(Tag.of("author", taskInfo.getAuthor()))
                )
                .increment();
    }
}
