package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class MealTestWatch extends Stopwatch {

    private static final Logger log = LoggerFactory.getLogger(MealTestWatch.class);

    private static final StringBuilder result = new StringBuilder("\n");

    private static long overallTestTime = 0;

    public static void logInfo(Description description, String status, long nanos) {
        long testTime = TimeUnit.NANOSECONDS.toMillis(nanos);
        overallTestTime += testTime;
        String testSummary = status + String.format("%-25s", description.getMethodName()) + testTime + " ms\n";
        result.append(testSummary);
        log.info(testSummary);
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "✔ ", nanos);
    }

    public static String getResult() {
        return result.append(String.format("%-27s", "* Overall test time:"))
                .append(overallTestTime).append(" ms").toString();
    }
}
