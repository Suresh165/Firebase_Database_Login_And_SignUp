package com.example.intellify;

public class User {

    private String name;
    private String city;
    private String school;
    private String email;

    public User(){

    }

    public User(String name, String city, String school, String email) {
        this.name = name;
        this.city = city;
        this.school = school;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
