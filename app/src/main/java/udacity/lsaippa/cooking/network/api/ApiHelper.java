package udacity.lsaippa.cooking.network.api;

import java.util.List;

import io.reactivex.Single;
import udacity.lsaippa.cooking.network.model.Recipe;


public interface ApiHelper {
    Single<List<Recipe>> getRecipeApiCall();
}
