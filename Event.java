package com.example.eventapp2;

import java.util.Date;

public class Event {
    // Private member variables
    private String title;
    private String description;
    private String date;

    // Constructor
    public Event(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = String.valueOf(date);
    }

    public Event(String newEvent, String description, String date) {
    }

    // Getter for the title
    public String getTitle() {
        return title;
    }

    // Setter for the title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter for the description
    public String getDescription() {
        return description;
    }

    // Setter for the description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for the date
    public CharSequence getDate() {
        return date;
    }

    // Setter for the date
    public void setDate(String date) {
        this.date = date;
    }

    // Override toString method if needed (useful for ArrayAdapter)
    @Override
    public String toString() {
        return title + " - " + description;
    }
}

