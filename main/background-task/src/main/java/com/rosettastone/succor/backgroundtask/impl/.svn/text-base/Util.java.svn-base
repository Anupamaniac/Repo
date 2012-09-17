package com.rosettastone.succor.backgroundtask.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Utils
 */
public final class Util {

    private Util() { }

    /**
     * Serialize stacktrace to string
     * @param t
     * @return
     */
    public static String stacktraceToString(Throwable t) {
        final Writer result = new StringWriter();
        t.printStackTrace(new PrintWriter(result));
        return result.toString();
    }

}
