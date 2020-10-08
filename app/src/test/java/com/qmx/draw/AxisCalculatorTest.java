package com.qmx.draw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(Parameterized.class)
public class AxisCalculatorTest {
    private float value;
    private int exp;

    @Parameterized.Parameters
    public static Collection prepareData()
    {
        Object[][] objects = { {1f, 0}, {0.9f, -1}, {10f, 1},{-0.5f, -1}, {-2.3f, 0},{-200f, 2},{0f,0} };
        return Arrays.asList(objects);

    }
    public AxisCalculatorTest(float value, int exp)
    {
        this.value = value;
        this.exp = exp;
    }
    @Test
    public void testZeroPowCalForPositiveValue() {
        int res = AxisCalculator.calPow(value);
        assertEquals(exp, res);
    }
}