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
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.lsaippa.cooking.R;
import udacity.lsaippa.cooking.network.model.Recipe;

import static udacity.lsaippa.cooking.utils.AppConstants.RECIPE_TAG;

public class DetailRecipeFragment extends Fragment {

    Recipe recipe;

    @BindView(R.id.rv_detail)
    RecyclerView mRecyclerView;

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
}
