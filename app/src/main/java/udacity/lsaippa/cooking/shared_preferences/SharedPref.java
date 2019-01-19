package udacity.lsaippa.cooking.shared_preferences;

import udacity.lsaippa.cooking.network.model.Recipe;

public interface SharedPref {

    void saveWidgetRecipe(Recipe recipe);

    Recipe loadWidgetRecipe();
}
