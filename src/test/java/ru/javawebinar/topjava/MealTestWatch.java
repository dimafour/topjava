package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.util.concurrent.TimeUnit;

public class MealTestWatch extends Stopwatch {

    private static final StringBuilder result = new StringBuilder("\n");

    private static long overallTestTime = 0;

    public static void logInfo(Description description, String status, long nanos) {
        long testTime = TimeUnit.NANOSECONDS.toMillis(nanos);
        overallTestTime += testTime;
        String testSummary = status + String.format("%-25s", description.getMethodName()) + testTime + " ms\n";
        result.append(testSummary);
        System.out.println(testSummary);
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "✔ ", nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "X ", nanos);
    }

    public static String getResult() {
        return result.append(String.format("%-27s", "* Overall test time:"))
                .append(overallTestTime).append(" ms").toString();
    }
}
