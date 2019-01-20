package udacity.lsaippa.cooking;

import android.content.Intent;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import udacity.lsaippa.cooking.network.model.Recipe;
import udacity.lsaippa.cooking.network.model.Step;
import udacity.lsaippa.cooking.ui.detail.recipe.DetailRecipeActivity;
import udacity.lsaippa.cooking.ui.detail.step.DetailStepActivity;
import udacity.lsaippa.cooking.utils.MockJson;
import udacity.lsaippa.cooking.utils.RecyclerViewItemCountAssertion;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(AndroidJUnit4.class)

public class DetailRecipeActivitTest {

    private Recipe recipe;

    @Rule
    public IntentsTestRule<DetailRecipeActivity> mActivityRule =
            new IntentsTestRule<DetailRecipeActivity>(
                    DetailRecipeActivity.class, false, true) {

                @Override
                protected Intent getActivityIntent() {
                    recipe = MockJson.getMockRecipe();
                    Intent intent = new Intent();
                    intent.putExtra(Recipe.class.getSimpleName(), recipe);
                    return intent;
                }
            };


    @Test
    public void CheckIngredientsAndRecipes_ExpectedAreShown(){
        int ingredients = recipe.getIngredients().size();
        int steps= recipe.getSteps().size();


        onView(withId(R.id.rv_detail))
                .check(new RecyclerViewItemCountAssertion(ingredients + steps));
    }

    @Test
    public void CheckExoplayerIsShowInIntroduction_ExpectedYes(){

        int mIngredients = recipe.getIngredients().size();

        onView(withId(R.id.rv_detail))
                .perform(RecyclerViewActions.actionOnItemAtPosition(mIngredients + 1,
                        click()));


        onView(withId(R.id.exo_play))
                .check(matches(notNullValue()));

    }

    @Test
    public void ClickOpenFragmentCheck_ExpectedTwoFragmentsTabletOneSmartphone(){

        int mIngredients = recipe.getIngredients().size();

        onView(withId(R.id.rv_detail))
                .perform(RecyclerViewActions.actionOnItemAtPosition(mIngredients + 1,
                        click()));

        Step step = recipe.getSteps().get(mIngredients + 1);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        if ( isTablet ){
            onView(allOf(
                    withId(R.id.tv_step_description),
                    withText(step.getDescription())
            )).check(matches(isDisplayed()));

        } else {
            intended(allOf(hasComponent(DetailStepActivity.class.getName()), isInternal()));
        }

    }

    private Resources getResources() {
        return InstrumentationRegistry.getTargetContext().getResources();
    }

}
