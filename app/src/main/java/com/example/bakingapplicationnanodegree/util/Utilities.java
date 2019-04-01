package com.example.bakingapplicationnanodegree.util;

import com.example.bakingapplicationnanodegree.models.RecipesListItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utilities {

    public static String readJsonFile(String filename) {
        String json=null;
        try{
            InputStream is = MyApplication.getAppContext().getAssets().open(filename);
            int size = is.available();
            byte[] buffer= new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static String prepareIngredientToBeShown(RecipesListItem recipesListItem){
        StringBuilder fullIngredients=new StringBuilder();
        for (int i=0; i<recipesListItem.getIngredients().size();i++) {
            if (i != recipesListItem.getIngredients().size() - 1) {
                fullIngredients.append(recipesListItem.getIngredients().get(i).getIngredient());
                fullIngredients.append(" - ");
            }
            else{
                fullIngredients.append(recipesListItem.getIngredients().get(i).getIngredient());
            }
        }
        return fullIngredients.toString();
    }




}
