package com.example.bakingapplicationnanodegree.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class RecipesListItem implements Parcelable{

	@SerializedName("image")
	private String image;

	@SerializedName("servings")
	private int servings;

	@SerializedName("name")
	private String name;

	@SerializedName("ingredients")
	private ArrayList<IngredientsItem> ingredients;

	@SerializedName("id")
	private int id;

	@SerializedName("steps")
	private ArrayList<StepsItem> steps;

	protected RecipesListItem(Parcel in) {
		image = in.readString();
		servings = in.readInt();
		name = in.readString();
		id = in.readInt();
	}


	public static final Creator<RecipesListItem> CREATOR = new Creator<RecipesListItem>() {
		@Override
		public RecipesListItem createFromParcel(Parcel in) {
			return new RecipesListItem(in);
		}

		@Override
		public RecipesListItem[] newArray(int size) {
			return new RecipesListItem[size];
		}
	};

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setServings(int servings){
		this.servings = servings;
	}

	public int getServings(){
		return servings;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setIngredients(ArrayList<IngredientsItem> ingredients){
		this.ingredients = ingredients;
	}

	public ArrayList<IngredientsItem> getIngredients(){
		return ingredients;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setSteps(ArrayList<StepsItem> steps){
		this.steps = steps;
	}

	public ArrayList<StepsItem> getSteps(){
		return steps;
	}

	@Override
 	public String toString(){
		return 
			"RecipesListItem{" +
			"image = '" + image + '\'' + 
			",servings = '" + servings + '\'' + 
			",name = '" + name + '\'' + 
			",ingredients = '" + ingredients + '\'' + 
			",id = '" + id + '\'' + 
			",steps = '" + steps + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(image);
		dest.writeInt(servings);
		dest.writeString(name);
		dest.writeInt(id);
	}
}