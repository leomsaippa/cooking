package udacity.lsaippa.cooking.ui.detail.recipe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.lsaippa.cooking.R;
import udacity.lsaippa.cooking.network.model.Recipe;
import udacity.lsaippa.cooking.shared_preferences.AppSharedPref;
import udacity.lsaippa.cooking.widget.RecipeWidgetProvider;

import static udacity.lsaippa.cooking.utils.AppConstants.RECIPE_TAG;

@SuppressWarnings("ALL")
public class DetailRecipeFragment extends Fragment {

    Recipe recipe;

    @BindView(R.id.rv_detail)
    RecyclerView mRecyclerView;

    AppSharedPref appSharedPref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        appSharedPref = new AppSharedPref(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_recipe_details,container,false);

        ButterKnife.bind(this,view);


        Activity activity = getActivity();
        if(activity!=null){
            Intent intent = activity.getIntent();
            if (intent.hasExtra(RECIPE_TAG)) {
                recipe = intent.getParcelableExtra(RECIPE_TAG);
                setup();
            }
        }

        return view;

    }

    private void setup() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        DetailRecipeAdapter detailRecipeAdapter = new DetailRecipeAdapter();

        detailRecipeAdapter.setOnStepClickListener((DetailRecipeActivity)getActivity());
        mRecyclerView.setAdapter(detailRecipeAdapter);

        detailRecipeAdapter.setData(recipe.getIngredients(), recipe.getSteps());
        detailRecipeAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if ( itemId == R.id.add_widget){
            appSharedPref.saveWidgetRecipe(recipe);
            RecipeWidgetProvider.updateWidget(getActivity());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
