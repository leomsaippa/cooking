package udacity.lsaippa.cooking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import udacity.lsaippa.cooking.R;
import udacity.lsaippa.cooking.network.model.Recipe;
import udacity.lsaippa.cooking.shared_preferences.AppSharedPref;
import udacity.lsaippa.cooking.ui.detail.recipe.DetailRecipeActivity;

@SuppressWarnings("ALL")
public class RecipeWidgetProvider extends AppWidgetProvider {

    AppSharedPref appSharedPreferences;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        appSharedPreferences = new AppSharedPref(context);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }


    private void updateAppWidget(Context context,
                                 AppWidgetManager appWidgetManager,
                                 int appWidgetId){

        RemoteViews views = getIngredientsListRemoteView(context);

        Recipe recip = appSharedPreferences.loadWidgetRecipe();

        if (recip != null){
            views.setTextViewText(R.id.appwidget_recipe_title, recip.getName());
        }

        appWidgetManager.updateAppWidget(appWidgetId,views);

    }

    public static void updateWidget(Context context){

        Intent intent = new Intent(context, RecipeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, RecipeWidgetProvider.class);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        context.sendBroadcast(intent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_listView_ingredients);
    }

    private static RemoteViews getIngredientsListRemoteView(Context context){

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_ingredients);

        Intent intent = new Intent(context, RecipeWidgetService.class);

        views.setRemoteAdapter(R.id.appwidget_listView_ingredients, intent);
        views.setEmptyView(R.id.appwidget_listView_ingredients, R.id.appwidget_empty_view);

        Intent intentPending = new Intent(context, DetailRecipeActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, intentPending, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.appwidget_listView_ingredients, pendingIntent);

        return views;
    }
}
