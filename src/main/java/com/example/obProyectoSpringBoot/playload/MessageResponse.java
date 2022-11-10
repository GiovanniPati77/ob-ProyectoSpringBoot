package com.example.obProyectoSpringBoot.playload;

public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public MessageResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
