package com.example.fn;

import com.fnproject.fn.api.FnConfiguration;
import com.fnproject.fn.api.RuntimeContext;

public class HelloFunction {

    private String message;


    @FnConfiguration
    public void init(RuntimeContext ctx) {
        message = ctx.getConfigurationByKey("hello").orElse("Hello");
    }

    public Message handleRequest(Message input) {
        String name = (input == null || input.getMessage().isEmpty()) ? "world"  : input.getMessage();

        return new Message(message + ", " + name + "!");
    }

}