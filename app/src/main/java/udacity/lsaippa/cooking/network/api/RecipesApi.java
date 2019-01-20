package udacity.lsaippa.cooking.network.api;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import udacity.lsaippa.cooking.network.model.Recipe;

public interface RecipesApi {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Single<List<Recipe>> getRecipes();


}
