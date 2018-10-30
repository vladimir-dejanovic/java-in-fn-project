# Java in Serverless world with FN Project

This are code examples for my talk *Java in Serverless world with FN Project*

In order to start this examples you need to have FN installed, https://fnproject.io/ 

All examples are developed by using bellow versions of FN server and client 

```
$ fn version
  Client version: 0.5.15
  Server version:  0.3.595
```

## Basic simple example of FN function in java language - branch init-0.1

To create fn function in Java programing language type this

```
$ fn init --runtime java --trigger http <name of function>
```

In my case I will call my function **function1**, so command becomes

```
$ fn init --runtime java --trigger http function1
```

Let us exam what is generated for us by FN project

```
$ cd function1
$ find .

./src/main/java/com/example/fn/HelloFunction.java
./src/test/java/com/example/fn/HelloFunctionTest.java
./pom.xml
./func.yaml
```

If you check pom.xml you will see nothing special about it, it is plain pom.xml with some dependencies for fn-project, however this dependencies are needed only for testing purpose.
If this dependencies are removed, everything will still work without any issues, only unit tests will fail.

If you open src/main/java/com/example/fn/HelloFunction.java you will see that it is very simple class 

```java
package com.example.fn;

public class HelloFunction {

    public String handleRequest(String input) {
        String name = (input == null || input.isEmpty()) ? "world"  : input;

        return "Hello, " + name + "!";
    }

}
```

### Unit Testing in FN Project

If you open src/test/java/com/example/fn/HelloFunctionTest.java, you will see how unit test is done in FN project

```java
public class HelloFunctionTest {

    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void shouldReturnGreeting() {
        testing.givenEvent().enqueue();
        testing.thenRun(HelloFunction.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        assertEquals("Hello, world!", result.getBodyAsString());
    }

}
```

If you are experienced with JUnit tests, this shouldn't need any explanations.

Result is on branch **init-0.1**

## Updated unit test - branch init-0.2

Let us add one more unit test, where we will pass




Result is on branch **init-0.2**

