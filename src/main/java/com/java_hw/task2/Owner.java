package com.java_hw.task2;

public class Owner {
    private final int id;
    private final String name;
    private final String lastName;
    private final int age;

    public Owner(int id, String name, String lastName, int age) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }
}
