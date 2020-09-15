package com.FearMyGaze.FarmWeather;

public class Converter {
    //convert to celsius
    public static float convertFahrenheitToCelsius(float fahrenheit) {
        float result = ((fahrenheit - 32) * 5 / 9);
        return result;
    }

    //convert to fahrenheit
    public static float convertCelsiusToFahrenheit(float celsius){
        float result = ((celsius * 9) / 5) +32;
        return result;
    }
}
