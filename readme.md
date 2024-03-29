** @SpringBootTest**

```

When added in a Test controller it adds the whole spring context to the Application.

Not advised when testing only a controller unit since it adds the 
whole application context,thus when an application grows it becomes very slow.

```

**@WebMvcTest**

```
Its more preferred when Testing a controller since it adds only the relevant beans to 
the WEB.

Its Only loads the relevant beans to the WEB.

Remember that a controller is part of the WEB.

```

**@WebMvcTest(PostController.class)**

```
To make webMVC Even better we advise it to run beans of a specific controller class

```
**@AutoConfigureMockMvc**

```
Tests will not be running inside our main spring application context and because of this,
we use the AutoConfigureMock Mvc. (Will run an instance of the server, not apache/tomcat
but a simplet server that can run within our application.

@Autowired
MockMvc mockMvc

```

**Test containers**

```
Testcontainers is an open source framework for providing throwaway, 
lightweight instances of databases, message brokers, web browsers, 
or just about anything that can run in a Docker container
```


**Spring jdbc & spring jpa**

```
Libraries that help me in connecting to my database.

We have slice tests for dataJDBC and dataJPA, @DataJdbcTest @DataJDBC
When testing anything we may have added to repostitories.

Any implementation that i may have added on top of the jdbc /jpa, can be 
tested as an integration tests.

```

**UnitTests vs Integration Tests**
```
Unit testing is independent and can be done in isolation. 

Each unit is tested separately, and external dependencies are typically mocked up. Integration 
testing, however, depends on the units that are being integrated. 

It requires that all units be tested and functioning correctly before it can be performed.

```


**Integration Tests**

```
When writing integration tests for the repository, we only write tests for what spring boot is not
already testing, example custom repositories.

Test container enable us use whatever we are using in our production enviroment for our tests as well.
(For instance we can spin up a postgreSQL container then use it for our Tests)

For instance if i use postgres for my production enviroment yet for my tests i am using H2,there are 
some errors i may not catch.

Test containers help me introduce a database in my testing.


```
**Notes By**

```
Mbugua Caleb

```