package com.cs428.dit.diabetestracker;

import com.cs428.dit.diabetestracker.helpers.Monitor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

/**
 * Created by Yuchen on 4/7/16.
 */
@RunWith(Parameterized.class)
public class MonitorTest {
    public Queue<Double> inputNumber;
    public Boolean expectedResult;
    public Monitor monitor;

    @Before
    public void initialize() {
        monitor = new Monitor(3);
    }

    // Each parameter should be placed as an argument here
    // Every time runner triggers, it will pass the arguments
    // from parameters we defined in primeNumbers() method
    public MonitorTest(Queue<Double> inputNumber, Boolean expectedResult) {
        this.inputNumber = inputNumber;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection indicatorNumbers() {
        Queue<Double> queue = new LinkedList<>();
        queue.offer(11.0);
        queue.offer(12.0);
        queue.offer(13.0);
        return Arrays.asList(new Object[][]{
                {queue, true}
        });
    }

    @Test
    public void testMonitorPlanChecker() {

        monitor.setQueue(inputNumber);
        monitor.detectWarning();
        assertEquals(expectedResult, monitor.getWarning());
    }
}