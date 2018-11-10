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

```
package com.example.fn;

public class HelloFunction {

    public String handleRequest(String input) {
        String name = (input == null || input.isEmpty()) ? "world"  : input;

        return "Hello, " + name + "!";
    }

}
```

As you can see there is no real dependency on FN Project nowhere in the code. So you might ask your self how FN magic works. 
In order to answer that question open func.yaml file

```
$ cat func.yaml

schema_version: 20180708
name: function1
version: 0.0.1
runtime: java
build_image: fnproject/fn-java-fdk-build:jdk9-1.0.75
run_image: fnproject/fn-java-fdk:jdk9-1.0.75
cmd: com.example.fn.HelloFunction::handleRequest
format: http-stream
triggers:
- name: function1-trigger
  type: http
  source: /function1-trigger
```

- schema_version - schema version of fn project
- name - name of function
- version - version of function, it will be ramped up by default after every deployment
- runtime - language in which function is written, this is needed in order to know how to create fn function from code
- build_image - docker image used for building fn function
- run_image - docker image used for running fn function
- cmd - what is executed when request come for fn function
- triggers - here are defined triggers which can be used to invoke fn function

By using this data, FN project can create fn function from our code although there are no dependencies in code it self.


### Starting & deploying FN function

In order to deploy our fn function and start using it, follow this steps. 

First start FN Server by running this command 

```
$ fn start
```

Check that all is good by running this command 

```
$ fn version
```
output should be something like this

```
Client version: 0.5.15
Server version:  0.3.595

```

After this in directory of your function run this command 

```
$ fn deploy --app <app name> --local
```

This will deploy function to FN Server.
Paramater **--local** means that function will be deployed to local instance of fn server, and docker image will not be pushed to Docker Hub. This is useful during development, so that you don't pollute your Docker HUB.

Command, for example, should like this 

```
$ fn deploy --app myapp1 --local
```

Now we can invoke function by calling this command

```
$ fn invoke <app name> <function name>
```

in my case it would be 

```
$ fn invoke myapp1 function1

```


### Unit Testing in FN Project

If you open src/test/java/com/example/fn/HelloFunctionTest.java, you will see how unit test is done in FN project

```
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

Let us add one more unit test, where we will pass argument Developer. 

Code for unit test is very similar to init unit test and it goes like this

```
    @Test
    public void testWithBody() {
        testing.givenEvent().withBody("Developer").enqueue();
        testing.thenRun(HelloFunction.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        assertEquals("Hello, Developer!", result.getBodyAsString());
    }
```

Important part here is **withBody** which we use to pass parameter to our function. 

Result is on branch **init-0.2**

## JSON as input and output - branch init-0.3


Result is on branch **init-0.3**