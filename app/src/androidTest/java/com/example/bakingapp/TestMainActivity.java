package com.example.bakingapp;

import android.os.Handler;
import android.os.Looper;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;



@RunWith(AndroidJUnit4.class)
public class TestMainActivity {

    @Rule
    public ActivityTestRule<MainActivity>  activityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickRecyclerViewItemOpens_RecipeActivity(){
        new Thread(() -> {
            Looper.prepare();
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                onData(anything()).inAdapterView(withId(R.id.rv_main)).atPosition(1).perform(click());
                onView(withId(R.id.recipe_name)).check(matches(withText("Brownies")));
            }, 10000);
        }).run();
    }

}
