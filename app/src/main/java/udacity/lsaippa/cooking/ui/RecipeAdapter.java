package udacity.lsaippa.cooking.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import udacity.lsaippa.cooking.R;
import udacity.lsaippa.cooking.network.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ItemViewHolder> {

    private List<Recipe> recipes;
    public interface OnRecipeClickListener{
        void onRecipeItemClick(Recipe recipe);
    }

    private OnRecipeClickListener onRecipeClickListener;

    public void setOnRecipeClickListener(OnRecipeClickListener onRecipeClickListener){
        this.onRecipeClickListener = onRecipeClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        View view = layoutInflater.inflate(R.layout.recipe_view_holder, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView recipe;

        ItemViewHolder(@NonNull View view) {
            super(view);
            recipe = view.findViewById(R.id.recipe_item);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onRecipeClickListener.onRecipeItemClick(recipes.get(getAdapterPosition()));
        }
    }
}
