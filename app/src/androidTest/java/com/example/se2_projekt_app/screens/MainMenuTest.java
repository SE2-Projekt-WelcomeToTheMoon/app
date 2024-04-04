package com.example.se2_projekt_app.screens;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.NoActivityResumedException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.se2_projekt_app.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class MainMenuTest {

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
    public void testStartSPButtonIsDisplayed() {
        onView(ViewMatchers.withId(R.id.startSP))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testStartMPButtonIsDisplayed() {
        onView(ViewMatchers.withId(R.id.startMP))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testSettingsButtonIsDisplayed() {
        onView(ViewMatchers.withId(R.id.settings))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testHighscoreButtonIsDisplayed() {
        onView(ViewMatchers.withId(R.id.highscore))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testExitButtonIsDisplayed() {
        onView(ViewMatchers.withId(R.id.exit))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testStartSPButtonLaunchesSingleplayer() {
        onView(ViewMatchers.withId(R.id.startSP)).perform(click());
        Intents.intended(IntentMatchers.hasComponent(Singleplayer.class.getName()));
    }

    @Test
    public void testStartMPButtonLaunchesMultiplayer() {
        onView(ViewMatchers.withId(R.id.startMP)).perform(click());
        Intents.intended(IntentMatchers.hasComponent(Multiplayer.class.getName()));
    }

    @Test
    public void testSettingsButtonLaunchesSettings() {
        onView(ViewMatchers.withId(R.id.settings)).perform(click());
        Intents.intended(IntentMatchers.hasComponent(Settings.class.getName()));
    }

    @Test
    public void testHighscoreButtonLaunchesHighscore() {
        onView(ViewMatchers.withId(R.id.highscore)).perform(click());
        Intents.intended(IntentMatchers.hasComponent(Highscore.class.getName()));
    }


    // it takes 30~ seconds but i can't find a better resolution
//    @Test
//    public void testBackButtonClosesView() {
//        onView(ViewMatchers.withId(R.id.exit)).perform(click());
//        try {
//            Espresso.pressBackUnconditionally();
//            fail("Should have thrown NoActivityResumedException");
//        } catch (NoActivityResumedException expected) {
//        }
//    }
}