package dev.caleb.danson.posts;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    List<Post> findAll(){
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Post> findById(@PathVariable Integer id){
        return Optional.ofNullable(postRepository.findById(id).orElseThrow(PostNotFoundException::new));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Post create(@RequestBody @Valid Post post){
        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    Post update(@PathVariable Integer id, @RequestBody @Valid Post post){

        Optional<Post> exists = postRepository.findById(id);
        if(exists.isPresent()){
            //records are simpler because I do not have to use getters and setters
            Post updated = new Post(
                    exists.get().id(),
                    exists.get().userId(),
                    post.title(),
                    post.body(),
                    exists.get().version()
            );

            return postRepository.save(updated);

        }else{
            throw new PostNotFoundException();
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        postRepository.deleteById(id);
    }

}
