package com.example.student238033.kalkulatorbmi;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.student238033.kalkulatorbmi", appContext.getPackageName());
    }

    @Test (expected = IllegalArgumentException.class )
    public void for_zero_values_bmi_throw_exception()
    {
        MainActivity bmi = new MainActivity();
        double result = bmi.countBMI(0,0);
    }
    @Test (expected = IllegalArgumentException.class )
    public void for_negative_values_bmi_throw_exception()
    {
        MainActivity bmi = new MainActivity();
        double result = bmi.countBMI(-56,2);
    }

    @Test
    public void for_proper_values_bmi_return_result()
    {
        MainActivity bmi = new MainActivity();
        double result = bmi.countBMI(90,2);
        assertEquals(22.5,result,0.01);
    }

    @Test
    public void check_squerd()
    {
        MainActivity bmi = new MainActivity();
        double result = bmi.squared(2);
        assertEquals(4,result,0.001);
    }

    @Test (expected = IllegalArgumentException.class)
    public void converting_to_kg_negative_value()
    {
        OtherMain bmi = new OtherMain();
        double result = bmi.convertToKg(-200);
    }

    @Test (expected = IllegalArgumentException.class)
    public void converting_to_m_zero_value()
    {
        OtherMain bmi = new OtherMain();
        double result = bmi.convertToM(0,5);
    }

    @Test
    public void converting_to_m_proper_values()
    {
        OtherMain bmi = new OtherMain();
        double result = bmi.convertToM(6,7);
        assertEquals(2.0066,result,0.0001);
    }

    @Test
    public void converting_to_kg_proper_value()
    {
        OtherMain bmi = new OtherMain();
        double result = bmi.convertToKg(198.7);
        assertEquals(90.1288,result,0.001);
    }
}
