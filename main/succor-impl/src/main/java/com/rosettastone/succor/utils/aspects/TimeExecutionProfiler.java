package com.rosettastone.succor.utils.aspects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * User: mixim
 * Date: 22.09.11
 */
@Component
@Aspect
public class TimeExecutionProfiler {

    private static final Log LOG = LogFactory.getLog(TimeExecutionProfiler.class);

    @Around("com.rosettastone.succor.utils.aspects.SystemArchitecture.businessController()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        LOG.debug("ServicesProfiler.profile(): Going to call the method: " + pjp.getSignature().getName());
        Object output = pjp.proceed();
        LOG.debug("ServicesProfiler.profile(): Method execution completed.");
        long elapsedTime = System.currentTimeMillis() - start;
        LOG.debug("ServicesProfiler.profile(): Method execution time: " + elapsedTime + " milliseconds.");

        return output;
    }

    @After("execution (* com.rosettastone.succor..*.*(..))")
    public void profileMemory() {
        LOG.debug("JVM memory in use = " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
    }
}