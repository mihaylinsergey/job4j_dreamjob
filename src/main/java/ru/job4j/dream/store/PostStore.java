package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostStore {

    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private AtomicInteger count;

    private PostStore() {
        posts.put(new Integer(1), new Post(1, "Junior Java Job", "description_1", LocalDate.now()));
        posts.put(new Integer(2), new Post(2, "Middle Java Job", "description_2", LocalDate.now().plusDays(1)));
        posts.put(new Integer(3), new Post(3, "Senior Java Job", "description_3", LocalDate.now().minusDays(1)));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(count.incrementAndGet());
        posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }
}