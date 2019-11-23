package com.example.farmweather;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;

public class startTest {

    @Rule
    public ActivityScenarioRule<start>Activity = new ActivityScenarioRule<>(start.class);

    private String Town = "Serres";
    private String Latitude = "40.908477";
    private String Longtitude = "23.500473";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testEmptyTown(){
        Espresso.onView(withId(R.id.enter)).perform(click());
        Espresso.onView(withText("Ok")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
    }

    @Test
    public void testTown(){
        Espresso.onView(withId(R.id.GetTown)).perform(typeText(Town));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.enter)).perform(click());
        Espresso.onView(withId(R.id.location)).check(matches(withText(Town + ", GR")));
        Espresso.onView(withId(R.id.refresh)).perform(swipeDown());
        Espresso.onView(withId(R.id.list_town)).check(matches(withText("Για " + Town + ", GR")));
    }

    @Test
    public void testAddItemOnList(){
        Espresso.onView(withId(R.id.GetTown)).perform(typeText(Town));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.enter)).perform(click());
        Espresso.onView(withId(R.id.Row_add)).perform(scrollTo(),click());
        Espresso.onView(withText("ΑΚΥΡΟ")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
        Espresso.onView(withId(R.id.Row_add)).perform(click());
        Espresso.onView(withText("ΝΑΙ")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
        Espresso.onView(withId(R.id.list_town)).perform(click());
    }

    @Test
    public void testDeleteItemFromList(){
        Espresso.onView(withId(R.id.GetTown)).perform(typeText(Town));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.enter)).perform(click());
        Espresso.onView(withId(R.id.list_town)).perform(scrollTo(),click());
        Espresso.onData(anything()).inAdapterView(withId(R.id.ListView)).atPosition(0).perform(click());
        Espresso.onView(withText("Ok")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
        Espresso.onData(anything()).inAdapterView(withId(R.id.ListView)).atPosition(0).perform(longClick());
        Espresso.onView(withText("ΟΧΙ")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
        Espresso.onData(anything()).inAdapterView(withId(R.id.ListView)).atPosition(0).perform(longClick());
        Espresso.onView(withText("ΝΑΙ")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
    }

    @After
    public void tearDown() throws Exception {
    }
}