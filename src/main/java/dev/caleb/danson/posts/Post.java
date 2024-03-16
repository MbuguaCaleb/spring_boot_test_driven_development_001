package dev.caleb.danson.posts;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Post(
        @Id
        Integer id,
        Integer userId,
        String title,
        String body,
        @Version
        String version) {
}
