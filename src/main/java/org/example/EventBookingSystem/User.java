package org.example.EventBookingSystem;

public class User {

    private int id;
    private String name;
    private String email;

    public User(int id, String name, String email) {
        setUserId(id);
        setUserName(name);
        setUserEmail(email);
    }

    public int getUserId() {
        return id;
    }
    public String getUserName() {
        return name;
    }
    public String getUserEmail() {
        return email;
    }

    public void setUserId(int id) {
        this.id = id;
    }
    public void setUserName(String name) {
        this.name = name;
    }
    public void setUserEmail(String email) {
        this.email = email;
    }
}
