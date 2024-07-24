package com.janwojnar.nameageapp.common;

/**
 * Utility class for memory friendly preparation of logs.
 */
public class LogPreparer {

    /**
     * Do not instantiate.
     */
    private LogPreparer() {
    }

    /**
     * Method for log preparation.
     *
     * @param messages segments of message.
     * @return whole message.
     */
    public static String prepareLog(Object... messages) {
        StringBuilder sb = new StringBuilder();
        for (Object message : messages) {
            sb.append(message);
        }
        return sb.toString();
    }
}
