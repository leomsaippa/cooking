package udacity.lsaippa.cooking.ui.detail.step;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.lsaippa.cooking.R;
import udacity.lsaippa.cooking.network.model.Step;

import static udacity.lsaippa.cooking.utils.AppConstants.STEPS_RECIPE_TAG;
import static udacity.lsaippa.cooking.utils.AppConstants.STEP_TAG;

public class DetailStepActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    ArrayList<Step> steps;
    Step currentStep;
    int currentStepId;
    int quantitySteps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);

        setup();

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentStep = steps.get(tab.getPosition());
                openNewStepTab(currentStep);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setup() {
        ButterKnife.bind(this);
        mTabLayout.setupWithViewPager(mViewPager);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null || getIntent().hasExtra(STEP_TAG)) {
            steps = getIntent().getParcelableArrayListExtra(STEPS_RECIPE_TAG);
            currentStep = getIntent().getParcelableExtra(STEP_TAG);
            setToolbarNameRecipe("Recipe Steps");
            currentStepId = currentStep.getId();
            quantitySteps = steps.size();
        }

        for(int i=0;i<quantitySteps;i++){
            String tabShowing = String.valueOf(i);
            if(tabShowing.equals("0"))
                tabShowing = "Intro";
            mTabLayout.addTab(mTabLayout.newTab().setText(tabShowing));
        }


        //Todo move screen to correct position view
        TabLayout.Tab tab = mTabLayout.getTabAt(currentStepId);

        if(tab!=null)
            tab.select();

        openNewStepTab(currentStep);
    }

    private void openNewStepTab(Step currentStep) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragmentManager.beginTransaction();

        Bundle newBundle = new Bundle();
        newBundle.putParcelable(STEP_TAG, currentStep);
        Fragment fragment = new DetailStepFragment();
        fragment.setArguments(newBundle);

        fragTransaction.replace(R.id.step_container, fragment);
        fragmentManager.popBackStack();

        fragTransaction.commit();
    }

    private void setToolbarNameRecipe(String recipeName) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(recipeName);
        }
    }

}
