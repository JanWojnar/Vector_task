package com.janwojnar.nameageapp.common;

public class LogPreparer {
    private LogPreparer(){}

    public static String prepareLog(Object... messages) {
        StringBuilder sb = new StringBuilder();
        for (Object message : messages) {
            sb.append(message);
        }
        return sb.toString();
    }
}
