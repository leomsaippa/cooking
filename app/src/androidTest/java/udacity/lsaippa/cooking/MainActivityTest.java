package udacity.lsaippa.cooking;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.widget.TextView;

import udacity.lsaippa.cooking.ui.detail.recipe.DetailRecipeActivity;
import udacity.lsaippa.cooking.ui.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;

@SuppressWarnings("ALL")
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {


    private IdlingResource mIdlingResource;

    @Before
    public void setup() {
        mIdlingResource = mActivityRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After
    public void tearDown() {
        if (mIdlingResource != null){
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void clickOnItem_OpenDetailRecipeActivity() {

        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(allOf(hasComponent(DetailRecipeActivity.class.getName()), isInternal()));
    }

    @Test
    public void clickOnNutellaPie_OpenDetailWithCorrectToolbarName() {

        int mPositionToTest = 0;
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(mPositionToTest, click()));

        String mTitleRecipeToTest = "Nutella Pie";
        onView(allOf(instanceOf(TextView.class),
                withParent(withResourceName(mTitleRecipeToTest))));
    }
}
