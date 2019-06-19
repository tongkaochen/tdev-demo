package com.tifone.tdev.demo.eventbus;

public class EventMessage {
    private String message;
    public EventMessage(String msg) {
        message = msg;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String msg) {
        message = msg;
    }
}
