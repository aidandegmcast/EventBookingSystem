package org.example.EventBookingSystem;

public class User {

    private String id;
    private String name;
    private String email;

    public User(String id, String name, String email) {
        setUserId(id);
        setUserName(name);
        setUserEmail(email);
    }

    public String getUserId() {
        return id;
    }
    public String getUserName() {
        return name;
    }
    public String getUserEmail() {
        return email;
    }

    public void setUserId(String id) {
        this.id = id;
    }
    public void setUserName(String name) {
        this.name = name;
    }
    public void setUserEmail(String email) {
        this.email = email;
    }
}
