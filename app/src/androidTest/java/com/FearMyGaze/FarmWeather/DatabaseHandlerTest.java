package com.FearMyGaze.FarmWeather;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class DatabaseHandlerTest {
    private String Town = "Serres";
    private DatabaseHandler mDataSource;

    @Rule
    public ActivityScenarioRule<start> Activity = new ActivityScenarioRule<>(start.class);

    @Before
    public void setUp() throws Exception {
        mDataSource = new DatabaseHandler(InstrumentationRegistry.getInstrumentation().getTargetContext());
        mDataSource.getWritableDatabase();
    }

    @Test
    public void test(){
        assertNotNull(mDataSource);
    }

    @Test
    public void testAddData(){
        mDataSource.insertData(Town + ", GR", "12/12/12", "29", "Καθαρός", "2", "79");
        Espresso.onView(withId(R.id.GetTown)).perform(typeText(Town));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.enter)).perform(click());
        Espresso.onView(withId(R.id.list_town)).perform(scrollTo(),click());

    }

    @After
    public void tearDown() throws Exception {
        mDataSource.clearall(Town + ", GR");
        mDataSource.close();
    }
}