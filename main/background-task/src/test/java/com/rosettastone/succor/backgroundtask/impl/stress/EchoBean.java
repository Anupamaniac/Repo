package com.rosettastone.succor.backgroundtask.impl.stress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EchoBean {

    private final Log log = LogFactory.getLog(EchoBean.class);

    public void printEcho(String message) {
        log.debug(message);
    }
}
