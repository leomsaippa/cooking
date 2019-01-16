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
import udacity.lsaippa.cooking.network.model.Step;

public class DetailRecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private List<Ingredient> ingredients;
    private List<Step> steps;

    private static final int INGREDIENT = 0;
    private static final int STEP = 1;

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
    public  RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        View view;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType){
            case INGREDIENT:
                view = layoutInflater.inflate(R.layout.detail_recipe_ingredients_view_holder,viewGroup,false);
                viewHolder = new IngredientsViewHolder(view);
                break;
            case STEP:
                view = layoutInflater.inflate(R.layout.detail_recipe_steps_view_holder, viewGroup, false);
                viewHolder = new StepsViewHolder(view);
                break;
            default:
                view = layoutInflater.inflate(R.layout.detail_recipe_steps_view_holder, viewGroup, false);
                viewHolder = new StepsViewHolder(view);
                break;

        }

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        int viewType = getItemViewType(i);

        switch (viewType){

            case INGREDIENT:
                Ingredient ingredient = ingredients.get(i);
                IngredientsViewHolder ingredientViewHolder = (IngredientsViewHolder) viewHolder;

                ingredientViewHolder.mIngredient.setText(ingredient.getIngredient());

                String quantityAndMeasure = String.format(ingredientViewHolder.itemView.getContext().getString(R.string.ingredient_quantity_and_measure),
                        ingredient.getQuantity(), ingredient.getMeasure());

                ingredientViewHolder.mQuantity.setText(quantityAndMeasure);

                break;
            case STEP:
                Step step = steps.get(getStepPosition(i));
                StepsViewHolder stepsViewHolder = (StepsViewHolder) viewHolder;
                String position = String.valueOf(getStepPosition(i));
                if(position.equals("0"))
                    position = "";
                String stepNumberText = position + "-" + step.getShortDescription();
                stepsViewHolder.mStepName.setText(stepNumberText);

                break;
        }



    }

    @Override
    public int getItemViewType(int position) {
        if(position<ingredients.size()){
            return INGREDIENT;
        }else{
            return STEP;
        }
    }


    @Override
    public int getItemCount() {
        int total = 0;
        if(steps!=null){
            total = steps.size();
        }
        if(ingredients!=null){
            total = total + ingredients.size();
        }
        return total;
    }

    public int getStepPosition(int adapterPosition){
        return adapterPosition - ingredients.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mStepName;

        StepsViewHolder(@NonNull View view) {
            super(view);
            mStepName = view.findViewById(R.id.tv_step_name);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Step step = steps.get(getStepPosition(getAdapterPosition()));
            onStepClickListener.onClickStep(step);        }
    }
    public class IngredientsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mIngredient;
        private final TextView mQuantity;

        IngredientsViewHolder(@NonNull View view) {
            super(view);
            mIngredient = view.findViewById(R.id.tv_ingredient_name);
            mQuantity = view.findViewById(R.id.tv_ingredient_qnt);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Step step = steps.get(getStepPosition(getAdapterPosition()));
            onStepClickListener.onClickStep(step);
        }
    }
}
