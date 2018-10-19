package com.example.fn;

public class HelloFunction {

    private String message;

    public HelloFunction() {
        message = "Hello";
    }

    public Message handleRequest(Message input) {
        String name = (input == null || input.getMessage().isEmpty()) ? "world"  : input.getMessage();

        return new Message(message + ", " + name + "!");
    }

}