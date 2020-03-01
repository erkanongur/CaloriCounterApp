package com.example.diyetkolik;

public class Item {
    private String name, calori, about;
    private int number;

    public Item() {
    }

    public Item(String name, String calori, String about) {
        this.name = name;
        this.calori = calori;
        this.about = about;
        number = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalori() {
        return calori;
    }

    public void setCalori(String calori) {
        this.calori = calori;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
