package dev.caleb.danson.posts;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJdbcTest // a slice test for only JDBC
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // i am setting up a Test database, but there is no need to replace since i am creating one
//we are teling spring boot do not replace but use the container i am spinning down below
public class PostRepositoryTest {

    //bringing in a Postgres SQL Container
    //static means we shall use the container between tests instead of firing up a new one (java knowledge)
    //remember that a repository is just a layer that is on top of your underlying database
    //when i am using a postgres database and i want to do someTests i will bring in a postgres container
    @Container
    @ServiceConnection // tells spring boot to configure the test container by itself.(Before spring boot 3.1.1 we had to do all the configs manually)
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    //the repository will work in the context of my TestContainer
    @Autowired
    PostRepository postRepository;

    /** Prior spring boot 3.1.1 after adding my test container, that is setting my datasource, but now i just need to add @ServerConnection annotation
    @DynamicPropertySource
    static void dataSourceProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url","");
    }
    **/

    @Test
    void connectionEstablished(){
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @BeforeEach
    void setUp(){
        List<Post> posts = List.of(
                new Post(1, 1, "Hello World", "This is my first Post", null),
                new Post(2, 1, "Second Post", "This is my second Post", null)
        );
        postRepository.saveAll(posts);
    }


    //Test for my custom repository methods
    @Test
    void shouldReturnPostsByTitle(){
        Post post = postRepository.findByTitle("Hello World");
        assertThat(post).isNotNull();
    }

}
