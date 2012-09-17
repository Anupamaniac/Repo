package com.rosettastone.succor.utils.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * User: mixim
 * Date: 22.09.11
 */
@Component
@Aspect
public class SystemArchitecture {

    @Pointcut("within(com.rosettastone.succor.service..*)")
    private void isService() {}

    @Pointcut("within(com.rosettastone.succor.parature..*)")
    private void isParature() {}

    @Pointcut("isService() || isParature()")
    public void businessController() { }
}