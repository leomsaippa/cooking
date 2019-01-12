package udacity.lsaippa.cooking.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import udacity.lsaippa.cooking.R;
import udacity.lsaippa.cooking.network.model.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener, RecipesFragment.GetMoviesCallbacks {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_recipes)
    RecyclerView mRecycleView;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.pb_main)
    ProgressBar mProgressBar;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tv_generic_error)
    TextView mTvGenericError;

    @BindView(R.id.btn_retry)
    Button mBtnRetry;


    private RecipeAdapter mRecipeAdapter;
    private RecipesFragment mRecipesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ButterKnife.bind(this);
        
        setup();

        FragmentManager fm = getSupportFragmentManager();
        mRecipesFragment = (RecipesFragment) fm.findFragmentByTag(RecipesFragment.TAG);

        if (mRecipesFragment == null) {
            mRecipesFragment = new RecipesFragment();
            fm.beginTransaction().add(mRecipesFragment, RecipesFragment.TAG).commit();

            getRecipes();
        } else {
            onRecipeLoadResult(mRecipesFragment.getRetainRecipes());
        }

    }

    private void setup() {
        GridLayoutManager layoutManager;

        layoutManager = new GridLayoutManager(this, 2);

        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter();
        mRecipeAdapter.setOnRecipeClickListener(this);
        mRecycleView.setAdapter(mRecipeAdapter);
    }


    public void getRecipes() {
        showLoading();
        mRecipesFragment.getData();
    }

    private void showRecipes() {
        mRecycleView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mTvGenericError.setVisibility(View.INVISIBLE);
        mBtnRetry.setVisibility(View.INVISIBLE);
    }

    private void showLoading() {
        mRecycleView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mTvGenericError.setVisibility(View.INVISIBLE);
        mBtnRetry.setVisibility(View.INVISIBLE);
    }

    private void showLoadingError() {
        mRecycleView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mTvGenericError.setVisibility(View.VISIBLE);
        mBtnRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRecipeItemClick(Recipe recipe) {
        Log.d("test","Abrir detail");

    }

    @Override
    public void onRecipeLoadResult(List<Recipe> recipes) {
        showRecipes();
        mRecipeAdapter.setRecipes(recipes);
        mRecipeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecipeLoadError(Throwable throwable) {
        showLoadingError();
    }
}
