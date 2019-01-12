package udacity.lsaippa.cooking.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import udacity.lsaippa.cooking.network.api.AppApiHelper;
import udacity.lsaippa.cooking.network.model.Recipe;

public class RecipesFragment extends Fragment {

    public static final String TAG = RecipesFragment.class.getSimpleName();

    private List<Recipe> retainRecipes;

    private CompositeDisposable compositeDisposable;

    private GetMoviesCallbacks mCallbacks;


    interface GetMoviesCallbacks {
        void onRecipeLoadResult(List<Recipe> recip);
        void onRecipeLoadError(Throwable throwable);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (MainActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mCallbacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    public List<Recipe> getRetainRecipes() {
        return retainRecipes;
    }

    public void setRetainRecipes(List<Recipe> retainRecipes) {
        this.retainRecipes = retainRecipes;
    }


    public void getData() {

        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(AppApiHelper.getInstance()
                .getRecipeApiCall()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Recipe>>() {
                    @Override
                    public void accept(List<Recipe> recipes) throws Exception {

                        setRetainRecipes(recipes);

                        if (mCallbacks != null) {
                            mCallbacks.onRecipeLoadResult(recipes);
                        } else {
                            Log.e(TAG, "Callback is null");
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        setRetainRecipes(null);

                        Log.e(TAG, "Throwable: " + throwable.toString());
                        if (mCallbacks != null) {
                            mCallbacks.onRecipeLoadError(throwable);
                        } else {
                            Log.e(TAG, "Callback is null");
                        }
                    }
                })
        );

    }


}
