package com.example.se2_projekt_app.screens;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.se2_projekt_app.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class HighscoreTest {

    @Rule
    public ActivityScenarioRule<Highscore> activityRule =
            new ActivityScenarioRule<>(Highscore.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testHighscoreButton1IsDisplayed() {
        onView(ViewMatchers.withId(R.id.highscore_button1))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testHighscoreButton2IsDisplayed() {
        onView(ViewMatchers.withId(R.id.highscore_button2))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testHighscoreBackButtonIsDisplayed() {
        onView(ViewMatchers.withId(R.id.highscore_back))
                .check(matches(isDisplayed()));
    }


    // it takes 30~ seconds but i can't find a better resolution
//    @Test
//    public void testBackButtonClosesView() {
//        onView(ViewMatchers.withId(R.id.highscore_back)).perform(click());
//        try {
//            Espresso.pressBackUnconditionally();
//            fail("Should have thrown NoActivityResumedException");
//        } catch (NoActivityResumedException expected) {
//        }
//    }

}
