package com.example.myapplication.models;

public class DropdownItem {
    private String label;
    private String value;

    public DropdownItem(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return label;  // This will be displayed in the spinner
    }
}