package com.cts.mfrp.Anvay.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final int MAX_RETRY = 2;
    private static ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);

    @Override
    public boolean retry(ITestResult result) {
        int current = count.get();
        if (current < MAX_RETRY) {
            count.set(current + 1);
            System.out.println("Retrying: " + result.getName()
                    + " attempt " + (current + 1) + " of " + MAX_RETRY);
            return true;
        }
        count.set(0);
        return false;
    }
}
