package com.company;

public class Ingredient {
    private String name, units;
    private double amount;

    public Ingredient (String name, String units, double amount) {
        this.name = name;
        this.units = units;
        this.amount = amount;
    }

    public Ingredient () {
        this.name = "";
        this.units = "";
        this.amount = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
