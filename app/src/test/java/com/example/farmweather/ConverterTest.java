package com.example.farmweather;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConverterTest {

    @Test
    public void convertFahrenheitToCelsius() {
        float input = 212;
        float output;
        float expected = 100;
        double delta = .1;

        Converter converter = new Converter();
        output = converter.convertFahrenheitToCelsius(input);

        assertEquals(expected, output, delta);
    }

    @Test
    public void convertCelsiusToFahrenheit() {
        float input = 100;
        float output;
        float expected = 212;
        double delta = .1;

        Converter converter = new Converter();
        output = converter.convertCelsiusToFahrenheit(input);

        assertEquals(expected, output, delta);
    }
}