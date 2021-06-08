package com.example.fellowtraveller2;

public class User {
    public String id, name, surname, email, image;
    public int rating;
    public boolean isDriver;

    public User() {
    }

    public User(String id, String name, String surname, String email, String image, int rating, boolean isDriver) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.image = image;
        this.rating = rating;
        this.isDriver = isDriver;
    }
}
