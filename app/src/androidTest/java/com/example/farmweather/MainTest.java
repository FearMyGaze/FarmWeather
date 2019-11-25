package com.example.farmweather;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MainTest {

    @Rule
    public ActivityScenarioRule <start> ActivityTester = new ActivityScenarioRule <> (start.class);

    //DHLWSH METABLHTWN GIA PERISOTERES PLHROFORIES
    private String TownTest ="Serres";
    private String LowUV = "Χαμηλός";
    private String HighUV = "Υψηλός";
    private String UV = "Κανονικός";
    private String LowRain = "Ελάχιστη";
    private String HighRain = "Μέγιστη";
    private String Rain = "Καθόλου";
    private String Animals = "Καμιά";
    private String DangerAnimals = "Κίνδυνος";
    private String SafeDrive = "Καμιά";
    private String RiskDrive = "Προσοχή";
    private String WaterPlant = "Όχι";
    private String DryPlant = "Ναι";
    private String Plant = "Κανονικό";

    //DHLWSH METABLHTWN GIA ELEXO KAIROU
    private String Clear = "Καθαρός";
    private String RainSky = "Βροχερός";
    private String Storm = "Καταιγίδα";
    private String Sun = "Λιακάδα";
    private String Heat = "Καύσωνας";
    private String Snow = "Χιόνι";
    private String Smoke = "Σκόνη";




    @Test
    public void testCelsiusToFahrenheit(){
        Espresso.onView(withId(R.id.GetTown)).perform(typeText(TownTest));                  //TownTest writes on GetTown
        Espresso.closeSoftKeyboard();                                                       //Close the Keyboard
        Espresso.onView(withId(R.id.enter)).perform(click());                               //Clicks the button
        Espresso.onView(withId(R.id.temperature)).perform(click());                         //Clicks the temperature
        Espresso.onView(withId(R.id.temperatureC)).check(matches(withText("F")));           //Checks if C change to F
    }

    @Test
    public void testStateIfClearWeather(){
        Espresso.onView(withId(R.id.GetTown)).perform(typeText(TownTest));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.enter)).perform(click());
        Espresso.onView(withId(R.id.status)).check(matches(withText(Clear)));
        Espresso.onView(withId(R.id.uv_index)).check(matches(withText(LowUV)));
        Espresso.onView(withId(R.id.rain_prob)).check(matches(withText(Rain)));
        Espresso.onView(withId(R.id.animal)).check(matches(withText(Animals)));
        Espresso.onView(withId(R.id.drive)).check(matches(withText(SafeDrive)));
        Espresso.onView(withId(R.id.water_plants)).check(matches(withText(Plant)));
    }

    @Test
    public void testStateIfHeatWeather(){
        Espresso.onView(withId(R.id.GetTown)).perform(typeText(TownTest));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.enter)).perform(click());
        Espresso.onView(withId(R.id.status)).check(matches(withText(Heat)));
        Espresso.onView(withId(R.id.uv_index)).check(matches(withText(HighUV)));
        Espresso.onView(withId(R.id.rain_prob)).check(matches(withText(Rain)));
        Espresso.onView(withId(R.id.animal)).check(matches(withText(Animals)));
        Espresso.onView(withId(R.id.drive)).check(matches(withText(SafeDrive)));
        Espresso.onView(withId(R.id.water_plants)).check(matches(withText(DryPlant)));
    }

    @Test
    public void testStateIfStormWeather(){
        Espresso.onView(withId(R.id.GetTown)).perform(typeText(TownTest));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.enter)).perform(click());
        Espresso.onView(withId(R.id.status)).check(matches(withText(Storm)));
        Espresso.onView(withId(R.id.uv_index)).check(matches(withText(LowUV)));
        Espresso.onView(withId(R.id.rain_prob)).check(matches(withText(HighRain)));
        Espresso.onView(withId(R.id.animal)).check(matches(withText(DangerAnimals)));
        Espresso.onView(withId(R.id.drive)).check(matches(withText(RiskDrive)));
        Espresso.onView(withId(R.id.water_plants)).check(matches(withText(WaterPlant)));
    }

    @Test
    public void testStateIfRainWeather(){
        Espresso.onView(withId(R.id.GetTown)).perform(typeText(TownTest));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.enter)).perform(click());
        Espresso.onView(withId(R.id.status)).check(matches(withText(RainSky)));
        Espresso.onView(withId(R.id.uv_index)).check(matches(withText(LowUV)));
        Espresso.onView(withId(R.id.rain_prob)).check(matches(withText(HighRain)));
        Espresso.onView(withId(R.id.animal)).check(matches(withText(Animals)));
        Espresso.onView(withId(R.id.drive)).check(matches(withText(RiskDrive)));
        Espresso.onView(withId(R.id.water_plants)).check(matches(withText(WaterPlant)));
    }

    @Test
    public void testStateIfSnowWeather(){
        Espresso.onView(withId(R.id.GetTown)).perform(typeText(TownTest));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.enter)).perform(click());
        Espresso.onView(withId(R.id.status)).check(matches(withText(Snow)));
        Espresso.onView(withId(R.id.uv_index)).check(matches(withText(LowUV)));
        Espresso.onView(withId(R.id.rain_prob)).check(matches(withText(Rain)));
        Espresso.onView(withId(R.id.animal)).check(matches(withText(Animals)));
        Espresso.onView(withId(R.id.drive)).check(matches(withText(RiskDrive)));
        Espresso.onView(withId(R.id.water_plants)).check(matches(withText(WaterPlant)));
    }

    @Test
    public void testStateIfSmokeWeather(){
        Espresso.onView(withId(R.id.GetTown)).perform(typeText(TownTest));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.enter)).perform(click());
        Espresso.onView(withId(R.id.status)).check(matches(withText(Smoke)));
        Espresso.onView(withId(R.id.uv_index)).check(matches(withText(LowUV)));
        Espresso.onView(withId(R.id.rain_prob)).check(matches(withText(Rain)));
        Espresso.onView(withId(R.id.animal)).check(matches(withText(Animals)));
        Espresso.onView(withId(R.id.drive)).check(matches(withText(SafeDrive)));
        Espresso.onView(withId(R.id.water_plants)).check(matches(withText(Plant)));
    }

    @Test
    public void testStateIfSunWeather(){
        Espresso.onView(withId(R.id.GetTown)).perform(typeText(TownTest));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.enter)).perform(click());
        Espresso.onView(withId(R.id.status)).check(matches(withText(Sun)));
        Espresso.onView(withId(R.id.uv_index)).check(matches(withText(LowUV)));
        Espresso.onView(withId(R.id.rain_prob)).check(matches(withText(LowRain)));
        Espresso.onView(withId(R.id.animal)).check(matches(withText(Animals)));
        Espresso.onView(withId(R.id.drive)).check(matches(withText(SafeDrive)));
        Espresso.onView(withId(R.id.water_plants)).check(matches(withText(Plant)));
    }

    @Test
    public void testGetIntoInfo(){
        Espresso.onView(withId(R.id.GetTown)).perform(typeText(TownTest));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.enter)).perform(click());
        Espresso.onView(withId(R.id.info)).perform(scrollTo(),click());
    }
}