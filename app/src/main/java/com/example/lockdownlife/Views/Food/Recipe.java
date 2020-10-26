package com.example.lockdownlife.Views.Food;

public class Recipe {

    private String title;
    private String href;
    private String ingredients;
    private String thumbnail;

    public Recipe(String title, String href, String ingredients, String thumbnail) {

        this.title = title;
        this.href = href;
        this.ingredients = ingredients;
        this.thumbnail = thumbnail;

    }

    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final String getHref() {
        return this.href;
    }

    public final void setHref(String href) {
        this.href = href;
    }


    public final String getIngredients() {
        return this.ingredients;
    }

    public final void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }


    public final String getThumbnail() {
        return this.thumbnail;
    }

    public final void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }




}
