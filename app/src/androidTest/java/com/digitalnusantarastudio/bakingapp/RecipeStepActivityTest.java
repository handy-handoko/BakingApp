package com.digitalnusantarastudio.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import com.digitalnusantarastudio.bakingapp.activities.RecipeStepActivity;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.google.android.exoplayer2.ExoPlayer.STATE_BUFFERING;
import static com.google.android.exoplayer2.ExoPlayer.STATE_READY;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assume.assumeTrue;

/**
 * Created by luqman on 03/09/17.
 */

public class RecipeStepActivityTest {

    //RecipeStepActivity get some intent data from Main Activity
    @Rule
    public ActivityTestRule<RecipeStepActivity> mActivityTestRule = new ActivityTestRule<RecipeStepActivity>(RecipeStepActivity.class){
        @Override
        protected Intent getActivityIntent() {
            //references for intent on test. thanks to Barend Garvelink for tutorial
            // http://blog.xebia.com/android-intent-extras-espresso-rules/
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, RecipeStepActivity.class);

            //getString function not recognized here. so hardcode intent key
            result.putExtra("steps", "[\n" +
                    "      {\n" +
                    "        \"id\": 0,\n" +
                    "        \"shortDescription\": \"Recipe Introduction\",\n" +
                    "        \"description\": \"Recipe Introduction\",\n" +
                    "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4\",\n" +
                    "        \"thumbnailURL\": \"\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 1,\n" +
                    "        \"shortDescription\": \"Starting prep\",\n" +
                    "        \"description\": \"1. Preheat the oven to 350\\u00b0F. Butter a 9\\\" deep dish pie pan.\",\n" +
                    "        \"videoURL\": \"\",\n" +
                    "        \"thumbnailURL\": \"\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 2,\n" +
                    "        \"shortDescription\": \"Prep the cookie crust.\",\n" +
                    "        \"description\": \"2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.\",\n" +
                    "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4\",\n" +
                    "        \"thumbnailURL\": \"\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 3,\n" +
                    "        \"shortDescription\": \"Press the crust into baking form.\",\n" +
                    "        \"description\": \"3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.\",\n" +
                    "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4\",\n" +
                    "        \"thumbnailURL\": \"\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 4,\n" +
                    "        \"shortDescription\": \"Start filling prep\",\n" +
                    "        \"description\": \"4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.\",\n" +
                    "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4\",\n" +
                    "        \"thumbnailURL\": \"\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 5,\n" +
                    "        \"shortDescription\": \"Finish filling prep\",\n" +
                    "        \"description\": \"5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.\",\n" +
                    "        \"videoURL\": \"\",\n" +
                    "        \"thumbnailURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 6,\n" +
                    "        \"shortDescription\": \"Finishing Steps\",\n" +
                    "        \"description\": \"6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!\",\n" +
                    "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4\",\n" +
                    "        \"thumbnailURL\": \"\"\n" +
                    "      }\n" +
                    "    ]");
            result.putExtra("name", "Nutella Pie");
            result.putExtra("id", 1);
            result.putExtra("ingredients", "[\n" +
                    "      {\n" +
                    "        \"quantity\": 2,\n" +
                    "        \"measure\": \"CUP\",\n" +
                    "        \"ingredient\": \"Graham Cracker crumbs\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"quantity\": 6,\n" +
                    "        \"measure\": \"TBLSP\",\n" +
                    "        \"ingredient\": \"unsalted butter, melted\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"quantity\": 0.5,\n" +
                    "        \"measure\": \"CUP\",\n" +
                    "        \"ingredient\": \"granulated sugar\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"quantity\": 1.5,\n" +
                    "        \"measure\": \"TSP\",\n" +
                    "        \"ingredient\": \"salt\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"quantity\": 5,\n" +
                    "        \"measure\": \"TBLSP\",\n" +
                    "        \"ingredient\": \"vanilla\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"quantity\": 1,\n" +
                    "        \"measure\": \"K\",\n" +
                    "        \"ingredient\": \"Nutella or other chocolate-hazelnut spread\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"quantity\": 500,\n" +
                    "        \"measure\": \"G\",\n" +
                    "        \"ingredient\": \"Mascapone Cheese(room temperature)\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"quantity\": 1,\n" +
                    "        \"measure\": \"CUP\",\n" +
                    "        \"ingredient\": \"heavy cream(cold)\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"quantity\": 4,\n" +
                    "        \"measure\": \"OZ\",\n" +
                    "        \"ingredient\": \"cream cheese(softened)\"\n" +
                    "      }\n" +
                    "    ]");
            return result;
        }
    };

    /**
     * this test is for testing ingredients Textview. on click, it should show all ingredients in Recycler View
     */
    @Test
    public void clickIngredients(){
        onView(withId(R.id.txtIngredients)).perform(click());

        onView(withId(R.id.ingredients_recycler_view)).check(new RecyclerViewItemCountAssertion(9));
    }

    /**
     * test step with video click. video should played
     */
    @Test
    public void clickVideoStep(){
        onView(withId(R.id.recipe_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //on first clicked, should show desc and video player
        String recipeStep = "Recipe Introduction";
        onView(withId(R.id.txtDescription)).check(matches(withText(recipeStep)));
        onView(allOf(withId(R.id.playerView),
                withClassName(is(SimpleExoPlayerView.class.getName())))).check(new VideoPlaybackAssertion(true));
        onView(withId(R.id.stepImageView)).check(matches(not(isDisplayed())));
    }

    /**
     * test step with video click.
     */
    @Test
    public void clickImageStep(){
        onView(withId(R.id.recipe_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));

        //on first clicked, should show desc and video player
        String recipeStep = "5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.";
        onView(withId(R.id.txtDescription)).check(matches(withText(recipeStep)));
        onView(allOf(withId(R.id.playerView),
                withClassName(is(SimpleExoPlayerView.class.getName())))).check(matches(not(isDisplayed())));
        onView(withId(R.id.stepImageView)).check(matches(isDisplayed()));
    }

    /**
     * test navigation.
     */
    @Test
    public void switchBetweenStepAndIngredients(){
        if(isTablet(mActivityTestRule.getActivity())){
        String recipeStep = "Recipe Introduction";
        onView(withId(R.id.recipe_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.txtDescription)).check(matches(withText(recipeStep)));

        onView(withId(R.id.txtIngredients)).perform(click());

        onView(withId(R.id.ingredients_recycler_view)).check(new RecyclerViewItemCountAssertion(9));

        onView(withId(R.id.recipe_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.txtDescription)).check(matches(withText(recipeStep)));
        }
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    // based on nenick answer here.
    // https://stackoverflow.com/questions/36399787/how-to-count-recyclerview-items-with-espresso
    public class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }

    //https://stackoverflow.com/questions/46016895/ambiguousviewmatcherexception-simpleexoplayerview-android
    class VideoPlaybackAssertion implements ViewAssertion {

        private final Matcher<Boolean> matcher;

        //Constructor
        public VideoPlaybackAssertion(Matcher<Boolean> matcher) {
            this.matcher = matcher;
        }

        //Sets the Assertion's matcher to the expected playbck state.
        public VideoPlaybackAssertion(Boolean expectedState) {
            this.matcher = is(expectedState);
        }

        //Method to check if the video is playing.
        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            SimpleExoPlayerView exoPlayerView = (SimpleExoPlayerView) view;
            SimpleExoPlayer exoPlayer = exoPlayerView.getPlayer();
            int state = exoPlayer.getPlaybackState();
            Boolean isPlaying;
            if ((state == STATE_BUFFERING) || (state == STATE_READY)) {
                isPlaying = true;
            } else {
                isPlaying = false;
            }
            assertThat(isPlaying, matcher);
        }

    }
}
