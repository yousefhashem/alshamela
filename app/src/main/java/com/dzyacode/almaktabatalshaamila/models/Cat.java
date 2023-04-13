package com.dzyacode.almaktabatalshaamila.models;

public class Cat {
    private String catName, num;

    public Cat() {
    }

    public Cat(String catName, String num) {
        this.catName = catName;
        this.num = num;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
