package com.example.assignment2;

public class entry {
    private int image;
    private String typeName;
    private double number;
    public entry(int image, String typeName, double number){
        this.image = image;
        this.typeName = typeName;
        this.number = number;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public double getNumber() {
        return number;
    }
}
