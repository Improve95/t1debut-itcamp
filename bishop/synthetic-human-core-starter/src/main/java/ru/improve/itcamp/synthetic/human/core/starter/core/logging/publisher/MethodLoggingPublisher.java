package ru.improve.itcamp.synthetic.human.core.starter.core.logging.publisher;

public interface MethodLoggingPublisher  {

    void publishLog(String methodName, Object[] args, Object result, long methodStartAt, long methodEndAt);

    PublisherType getPublisherType();
}
