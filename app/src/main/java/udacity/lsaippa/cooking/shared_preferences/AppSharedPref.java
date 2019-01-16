package udacity.lsaippa.cooking.shared_preferences;

import android.content.Context;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import udacity.lsaippa.cooking.network.model.Recipe;

public class AppSharedPref implements SharedPref{

    Context context;
    final SharedPreferences sharedPreferences;
    private final Gson gson;

    public AppSharedPref(Context context){
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
    }
    @Override
    public void saveWidgetRecipe(Recipe recipe) {
        String recipeJson = gson.toJson(recipe, Recipe.class);
        sharedPreferences.edit().putString(Recipe.class.getSimpleName(), recipeJson).apply();

    }

    @Override
    public Recipe loadWidgetRecipe() {
        try {
            String recipeJson =
                    sharedPreferences.getString(Recipe.class.getSimpleName(), "{}");

            return gson.fromJson(recipeJson, Recipe.class);

        } catch (Exception exception) {

            return null;
        }

    }
}

