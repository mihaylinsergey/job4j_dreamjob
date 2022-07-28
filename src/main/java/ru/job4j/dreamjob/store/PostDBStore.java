package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ThreadSafe
@Repository
public class PostDBStore {

    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Post post = new Post(it.getInt("id"), it.getString("name"));
                    post.setDescription(it.getString("description"));
                    post.setCreated(it.getDate("created").toLocalDate());
                    post.setVisible(it.getBoolean("visible"));
                    City city = new City();
                    city.setId(it.getInt("city_id"));
                    post.setCity(city);
                    posts.add(post);
                    System.out.println("PostDBStore");
                    posts.forEach(System.out::println);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(
                             "INSERT INTO post(name, description, city_id, created, visible) VALUES (?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setInt(3, post.getCity().getId());
            ps.setDate(4, Date.valueOf(post.getCreated()));
            ps.setBoolean(5, post.isVisible());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps =
                cn.prepareStatement(
                        "UPDATE post SET name = ?, description = ?, visible = ?, city_id = ? where id = ?")) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setBoolean(3, post.isVisible());
            ps.setInt(4, post.getCity().getId());
            ps.setInt(5, post.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    Post post = new Post(it.getInt("id"), it.getString("name"));
                    post.setDescription(it.getString("description"));
                    post.setCreated(it.getDate("created").toLocalDate());
                    post.setVisible(it.getBoolean("visible"));
                    City city = new City();
                    city.setId(it.getInt("city_id"));
                    post.setCity(city);
                    return post;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
