package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import java.time.LocalDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDBStoreTest {
    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        City city = new City();
        Post post = new Post(0, "Java Job", city);
        post.setCreated(LocalDate.now());
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        City city = new City();
        Post post = new Post(0, "Java Job", city);
        post.setCreated(LocalDate.now());
        store.add(post);
        post.setName("Update Java Job");
        store.update(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }
}