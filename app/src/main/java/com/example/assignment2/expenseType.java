package com.example.assignment2;

public class expenseType {

    private int image;
    private String name;

    public expenseType(int image, String name){
        this.image = image;
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
