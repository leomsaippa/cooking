package udacity.lsaippa.cooking.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import udacity.lsaippa.cooking.R;
import udacity.lsaippa.cooking.network.model.Ingredient;
import udacity.lsaippa.cooking.network.model.Recipe;
import udacity.lsaippa.cooking.shared_preferences.AppSharedPref;

public class RecipeWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplicationContext());
    }

    class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        AppSharedPref appSharedPref;
        private Recipe recipe ;
        private List<Ingredient> ingredientList;

        Context mContext;

        public RecipeRemoteViewsFactory(Context applicationContext) {
            mContext = applicationContext;
            appSharedPref = new AppSharedPref(mContext);
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            recipe = appSharedPref.loadWidgetRecipe();
            ingredientList = recipe.getIngredients();
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            if (ingredientList == null) return 0;
            return ingredientList.size();
        }


        @Override
        public RemoteViews getViewAt(int position) {
            if (position == AdapterView.INVALID_POSITION || ingredientList == null ||
                    ingredientList.isEmpty()) {
                return null;
            }

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_recipe_ingredient_item);

            Ingredient ingredient = ingredientList.get(position);

            String title = ingredient.getIngredient();

            String quantityAndMeasure = String.format(mContext.getString(R.string.ingredient_quantity_and_measure),
                    ingredient.getQuantity(), ingredient.getMeasure());

            views.setTextViewText(R.id.textView_ingredient_title, title);
            views.setTextViewText(R.id.textView_ingredient_quantity_and_measure, quantityAndMeasure);

            Intent fillIntent = new Intent();
            fillIntent.putExtra(Recipe.class.getSimpleName(), recipe);
            views.setOnClickFillInIntent(R.id.textView_ingredient_title, fillIntent);

            return views;

        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
