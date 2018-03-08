package com.company;

public class Record {

    private String day, shift;
    private int stoneOven, greenPlate;

    public Record(String day, String shift, int stoneOven, int greenPlate) {
        this.day = day;
        this.shift = shift;
        this.stoneOven = stoneOven;
        this.greenPlate = greenPlate;
    }

    public Record() {
        this.day = "";
        this.shift = "";
        this.stoneOven = 0;
        this.greenPlate = 0;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public int getStoneOven() {
        return stoneOven;
    }

    public void setStoneOven(int stoneOven) {
        this.stoneOven = stoneOven;
    }

    public int getGreenPlate() {
        return greenPlate;
    }

    public void setGreenPlate(int greenPlate) {
        this.greenPlate = greenPlate;
    }
}
