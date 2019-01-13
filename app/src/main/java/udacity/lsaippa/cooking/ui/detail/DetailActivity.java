package udacity.lsaippa.cooking.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import udacity.lsaippa.cooking.R;
import udacity.lsaippa.cooking.network.model.Recipe;
import udacity.lsaippa.cooking.network.model.Step;
import udacity.lsaippa.cooking.ui.detail.recipe.DetailRecipeAdapter;
import udacity.lsaippa.cooking.ui.detail.recipe.DetailRecipeFragment;

import static udacity.lsaippa.cooking.utils.AppConstants.RECIPE_TAG;

public class DetailActivity extends AppCompatActivity implements DetailRecipeAdapter.OnStepClickListener {

    Recipe recipe;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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


  /*      if (isTablet){

            Step step =  receiptExtra.getSteps().get(0);
            Fragment stepDetailFrg = StepDetailFrg.newInstance(step);

            fragTransaction.replace(R.id.container_step_detail, stepDetailFrg , StepDetailFrg.class.getSimpleName()) ;

        }*/

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
        Toast.makeText(this, "Abrir details: " + step.getShortDescription(), Toast.LENGTH_SHORT).show();
    }
}
