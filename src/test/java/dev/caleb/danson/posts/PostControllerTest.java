package dev.caleb.danson.posts;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc //helps me make mock web calls
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;


    List<Post> posts = new ArrayList<>();

    //I specify what i want to run before eachTest
    @BeforeEach
    void setUp() {
        //crate some posts
        posts = List.of(
                new Post(1, 1, "Hello World", "This is my first Post", null),
                new Post(2, 1, "Second Post", "This is my second Post", null)
        );
    }

    //Rest Api

    //list (Testing List all posts)
    @Test
    void shouldFindAllPosts() throws Exception {
        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk());
    }

}
