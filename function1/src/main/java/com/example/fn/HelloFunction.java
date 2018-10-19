package com.example.fn;

public class HelloFunction {

    public Message handleRequest(Message input) {
        String name = (input == null || input.getMessage().isEmpty()) ? "world"  : input.getMessage();

        return new Message("Hello, " + name + "!");
    }

}