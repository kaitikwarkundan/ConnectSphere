package com.connectsphere.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private int age;
    private List<String> interests;

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.interests = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public List<String> getInterests() { return interests; }

    public void addInterest(String interest) {
        interests.add(interest);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', age=" + age + "}";
    }
}