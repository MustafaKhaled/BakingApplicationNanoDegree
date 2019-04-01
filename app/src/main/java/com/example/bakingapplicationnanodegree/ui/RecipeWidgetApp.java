package com.example.bakingapplicationnanodegree.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import com.example.bakingapplicationnanodegree.R;
public class RecipeWidgetApp extends AppWidgetProvider {
    private static final String TAG = "RecipeWidgetApp";
    SharedPreferences sharedPreferences ;
    public static final String WIDGET_IDS_KEY ="mywidgetproviderwidgetids";
    public static final String WIDGET_DATA_KEY ="mywidgetproviderwidgetdata";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        sharedPreferences = context.getSharedPreferences("Recipe_file",Context.MODE_PRIVATE);
        for(int appWidget : appWidgetIds){
            Intent intent = new Intent(context,MainActivity.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(RecipeWidgetApp.WIDGET_IDS_KEY, appWidget);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
            Log.d(TAG, "onUpdate: Recipe name: "+sharedPreferences.getString("recipe_name",""));
            Log.d(TAG, "onUpdate: Recipe desc: "+sharedPreferences.getString("recipe_ing",""));
            remoteView.setTextViewText(R.id.recipe_name_widget,sharedPreferences.getString("recipe_name",""));
            remoteView.setTextViewText(R.id.recipe_ingredient_widget,sharedPreferences.getString("recipe_ing",""));

            remoteView.setOnClickPendingIntent(R.id.recipe_name_widget,pendingIntent);
            appWidgetManager.updateAppWidget(appWidget,remoteView);
        }

    }

    }

