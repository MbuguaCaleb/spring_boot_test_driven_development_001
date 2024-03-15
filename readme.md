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


**Notes By**

```
Mbugua Caleb

```