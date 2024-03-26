package dev.caleb.danson.posts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc //helps me make mock web calls
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostRepository postRepository;

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

        //correct response expected
        //during testing i have control of the data that i use to mock
        //if i have the data i want to test with and to assert of my methods bring the expected response it means my methods are correct
        //comparison of the right return by find all method
        String jsonResponse = """
                [{"id":1,
                  "userId":1,
                  "title":"Hello World",
                  "body":"This is my first Post",
                  "version":null
                },{"id":2,
                  "userId":1,
                  "title":"Second Post",
                  "body":"This is my second Post",
                  "version":null}
                ]
                """;

        //I am telling my find all in the context of Tests to return this
        when(postRepository.findAll()).thenReturn(posts);

        //i am testing my posts which invoke a find all() implementation
        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

    }


    // /api/posts/1
    @Test
    void shouldFindPostWhenGivenValidID() throws Exception {

        //Making my findOne return a single post
        //i have told my repository,have this date
        when(postRepository.findById(1)).thenReturn(Optional.of(posts.getFirst()));

        //Right response i expect when i have the data
        var post = posts.get(0);
        //Expected result
        String json = STR."""
                {
                    "id":\{post.id()},
                    "userId":\{post.userId()},
                    "title":"\{post.title()}",
                    "body":"\{post.body()}",
                    "version": null
                }
                """;

        //calling my controller to see if it gives me the data
        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

    }

    @Test
    void shouldNotFindPostWhenGivenInvalidID() throws Exception {

        when(postRepository.findById(999)).thenThrow(PostNotFoundException.class);

        mockMvc.perform(get("/api/posts/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewPostWhenPostIsValid() throws Exception {

        //Think of Test cases like before you have implemented code logic
        //This is my Input
        var post = new Post(3, 1, "New Post", "Caleb Masters Create", null);

        when(postRepository.save(post)).thenReturn(post);

        //asserting the JSON
        String json = STR."""
                {
                    "id":\{post.id()},
                    "userId":\{post.userId()},
                    "title":"\{post.title()}",
                    "body":"\{post.body()}",
                    "version": null
                }
                """;

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().json(json));

    }

    @Test
    void shouldNotCreateNewPostWhenPostIsInValid() throws Exception {
        var post = new Post(3, 1, "", "", null);

        //when(postRepository.save(post)).thenReturn(post);

        //asserting the JSON
        String json = STR."""
                {
                    "id":\{post.id()},
                    "userId":\{post.userId()},
                    "title":"\{post.title()}",
                    "body":"\{post.body()}",
                    "version": null
                }
                """;

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldUpdatePostsWhenGivenValidPosts() throws Exception {

        //I am only mocking the repositories, then finding my input and outPut
        //When i pass a post with an existing ID to the save method; it updates the POST
        var updated = new Post(1, 1, "Title Updated", "New Body Updated", 1);

        when(postRepository.findById(1)).thenReturn(Optional.of(posts.getFirst()));

        when(postRepository.save(updated)).thenReturn(updated);

        //asserting the JSON
        String requestBody = STR."""
                {
                    "id":\{updated.id()},
                    "userId":\{updated.userId()},
                    "title":"\{updated.title()}",
                    "body":"\{updated.body()}",
                    "version": null
                }
                """;

        mockMvc.perform(put("/api/posts/1")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());

    }

    @Test
    void shouldDeletePostWhenGivenValidID() throws Exception {

        doNothing().when(postRepository).deleteById(1);

        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isNoContent());

        //make sure delete by id one was called only once
        verify(postRepository, times(1)).deleteById(1);
    }

}
