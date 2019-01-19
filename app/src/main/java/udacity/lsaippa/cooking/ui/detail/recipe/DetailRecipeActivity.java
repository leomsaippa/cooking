package udacity.lsaippa.cooking.ui.detail.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import udacity.lsaippa.cooking.R;
import udacity.lsaippa.cooking.network.model.Recipe;
import udacity.lsaippa.cooking.network.model.Step;
import udacity.lsaippa.cooking.ui.detail.step.DetailStepActivity;
import udacity.lsaippa.cooking.ui.detail.step.DetailStepFragment;

import static udacity.lsaippa.cooking.utils.AppConstants.RECIPE_TAG;
import static udacity.lsaippa.cooking.utils.AppConstants.STEPS_RECIPE_TAG;
import static udacity.lsaippa.cooking.utils.AppConstants.STEP_TAG;

public class DetailRecipeActivity extends AppCompatActivity implements DetailRecipeAdapter.OnStepClickListener {

    private boolean isTablet;
    Recipe recipe;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null || getIntent().hasExtra(RECIPE_TAG)) {
            recipe = getIntent().getParcelableExtra(RECIPE_TAG);
            setToolbarNameRecipe(recipe.getName());
        }

        openRecipeFragment();
    }

    private void openRecipeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = new DetailRecipeFragment();
        fragmentTransaction.add(R.id.recipe_frame_container, fragment , DetailRecipeFragment.class.getSimpleName()) ;

        if (isTablet){

            Fragment fragmentStep = new DetailStepFragment();
            Bundle newBundle = new Bundle();
            Step currentStep =  recipe.getSteps().get(0);
            newBundle.putParcelable(STEP_TAG, currentStep);
            fragmentStep.setArguments(newBundle);
            fragmentTransaction.replace(R.id.recipe_frame_step_container, fragmentStep, DetailStepFragment.class.getSimpleName());

        }

        fragmentTransaction.commit();
    }

    private void setToolbarNameRecipe(String recipeName){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(recipeName);
        }


    }

    @Override
    public void onClickStep(Step step) {
        if (isTablet) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragmentStep = new DetailStepFragment();
            Bundle newBundle = new Bundle();
            Step currentStep =  recipe.getSteps().get(0);
            newBundle.putParcelable(STEP_TAG, currentStep);
            fragmentStep.setArguments(newBundle);
            fragmentTransaction.replace(R.id.recipe_frame_step_container, fragmentStep, DetailStepFragment.class.getSimpleName());
            fragmentTransaction.commit();

        } else {
            Intent intent = new Intent(DetailRecipeActivity.this,DetailStepActivity.class);
            intent.putExtra(STEP_TAG, step);
            intent.putParcelableArrayListExtra(STEPS_RECIPE_TAG,recipe.getSteps());

            startActivity(intent);

        }
    }
}
