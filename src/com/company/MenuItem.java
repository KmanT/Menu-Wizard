package com.company;

import javafx.collections.ObservableList;

import java.util.LinkedList;

public class MenuItem {

    private String name;
    private ObservableList<Ingredient> Ingredients;

    public MenuItem(String name, ObservableList<Ingredient> Ingredients) {
        this.name = name;
        this.Ingredients = Ingredients;
    }

    public MenuItem() {
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObservableList<Ingredient> getIngredients() {
        return Ingredients;
    }

    public void setIngredients(ObservableList<Ingredient> Ingredients) {
        this.Ingredients = Ingredients;
    }
}
