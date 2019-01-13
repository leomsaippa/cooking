package udacity.lsaippa.cooking.ui.detail.recipe;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import udacity.lsaippa.cooking.R;
import udacity.lsaippa.cooking.network.model.Ingredient;
import udacity.lsaippa.cooking.network.model.Recipe;
import udacity.lsaippa.cooking.network.model.Step;
import udacity.lsaippa.cooking.ui.main.RecipeAdapter;

public class DetailRecipeAdapter extends RecyclerView.Adapter<DetailRecipeAdapter.ItemViewHolder>{


    private List<Ingredient> ingredients;
    private List<Step> steps;

    public interface OnStepClickListener{
        void onClickStep(Step step);
    }

    private OnStepClickListener onStepClickListener;

    public void setOnStepClickListener(OnStepClickListener onStepClickListener) {
        this.onStepClickListener = onStepClickListener;
    }

    void setData(List<Ingredient> ingredients,
                 List<Step> steps){
        this.ingredients = ingredients;
        this.steps = steps;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        View view = layoutInflater.inflate(R.layout.detail_recipe_view_holder, viewGroup, false);
        return new ItemViewHolder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {

        Ingredient ingredient = ingredients.get(i);
        Step step = steps.get(i);
        itemViewHolder.mIngredient.setText(ingredient.getIngredient());

        String quantityAndMeasure = String.format(itemViewHolder.itemView.getContext().getString(R.string.ingredient_quantity_and_measure),
                ingredient.getQuantity(), ingredient.getMeasure());

        itemViewHolder.mQuantity.setText(quantityAndMeasure);
        String stepNumberText = i  + 1 + " )" + step.getShortDescription();
        itemViewHolder.mStepName.setText(stepNumberText);


    }

    @Override
    public int getItemCount() {
        return 5;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mIngredient;
        private final TextView mQuantity;
        private final TextView mStepName;

        ItemViewHolder(@NonNull View view) {
            super(view);
            mIngredient = view.findViewById(R.id.tv_ingredient_name);
            mQuantity = view.findViewById(R.id.tv_ingredient_qnt);
            mStepName = view.findViewById(R.id.tv_step_name);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Step step = steps.get(getAdapterPosition());
            onStepClickListener.onClickStep(step);        }
    }
}
