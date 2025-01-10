package com.cryptocurrency.data.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics {
    private final Counter requestCounter;

    public CustomMetrics(MeterRegistry registry) {
        this.requestCounter = registry.counter("custom_requests_total", "service", "example");
    }

    public void incrementRequestCount() {
        requestCounter.increment();
    }
}
