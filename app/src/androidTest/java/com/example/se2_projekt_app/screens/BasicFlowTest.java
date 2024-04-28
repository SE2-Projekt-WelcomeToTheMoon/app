package com.example.se2_projekt_app.screens;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.se2_projekt_app.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

public class BasicFlowTest {

    @Rule
    public ActivityScenarioRule<MainMenu> activityRule =
            new ActivityScenarioRule<>(MainMenu.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testSingleplayerFlow() {
        onView(ViewMatchers.withId(R.id.startSP)).perform(click());
        Intents.intended(IntentMatchers.hasComponent(Singleplayer.class.getName()));
        onView(ViewMatchers.withId(R.id.singleplayer_back)).perform(click());
        onView(ViewMatchers.withId(R.id.startSP)).check(matches(isDisplayed()));
    }
    @Test
    public void testMultiplayerFlow1() {
        onView(ViewMatchers.withId(R.id.startMP)).perform(click());
        Intents.intended(IntentMatchers.hasComponent(Multiplayer.class.getName()));
        onView(ViewMatchers.withId(R.id.backButton)).perform(click());
        onView(ViewMatchers.withId(R.id.startMP)).check(matches(isDisplayed()));
    }

    @Test
    public void testSettingsFlow() {
        onView(ViewMatchers.withId(R.id.settings)).perform(click());
        Intents.intended(IntentMatchers.hasComponent(Settings.class.getName()));
        onView(ViewMatchers.withId(R.id.settings_back)).perform(click());
        onView(ViewMatchers.withId(R.id.settings)).check(matches(isDisplayed()));
    }

    @Test
    public void testHighscoreFlow() {
        onView(ViewMatchers.withId(R.id.highscore)).perform(click());
        Intents.intended(IntentMatchers.hasComponent(Highscore.class.getName()));
        onView(ViewMatchers.withId(R.id.highscore_back)).perform(click());
        onView(ViewMatchers.withId(R.id.highscore)).check(matches(isDisplayed()));
    }
}
