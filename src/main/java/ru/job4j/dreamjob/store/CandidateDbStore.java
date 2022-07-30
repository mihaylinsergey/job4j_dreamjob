package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ThreadSafe
@Repository
public class CandidateDbStore {

    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(CandidateDbStore.class.getName());

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidates")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Candidate candidate = new Candidate(it.getInt("id"), it.getString("name"));
                    candidate.setDescription(it.getString("description"));
                    candidate.setCreated(it.getDate("created").toLocalDate());
                    candidate.setVisible(it.getBoolean("visible"));
                    candidate.setPhoto(it.getBytes("photo"));
                    City city = new City();
                    city.setId(it.getInt("city_id"));
                    candidate.setCity(city);
                    candidates.add(candidate);
                }
            }
        } catch (Exception e) {
            LOG.error("Error!", e);
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(
                             "INSERT INTO candidates(name, description, created, visible, city_id, photo) VALUES (?, ?, ?, ?, ?, ?)",
                             PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setDate(3, Date.valueOf(candidate.getCreated()));
            ps.setBoolean(4, candidate.isVisible());
            ps.setInt(5, candidate.getCity().getId());
            ps.setBytes(6, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Error!", e);
        }
        return candidate;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(
                             "UPDATE candidates SET name = ?, description = ?, visible = ?, city_id = ?, photo = ? where id = ?")) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setBoolean(3, candidate.isVisible());
            ps.setInt(4, candidate.getCity().getId());
            ps.setBytes(5, candidate.getPhoto());
            ps.setInt(6, candidate.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error("Error!", e);
        }
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidates WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    Candidate candidate = new Candidate(it.getInt("id"), it.getString("name"));
                    candidate.setDescription(it.getString("description"));
                    candidate.setCreated(it.getDate("created").toLocalDate());
                    candidate.setVisible(it.getBoolean("visible"));
                    candidate.setPhoto(it.getBytes("photo"));
                    City city = new City();
                    city.setId(it.getInt("city_id"));
                    candidate.setCity(city);
                    return candidate;
                }
            }
        } catch (Exception e) {
            LOG.error("Error!", e);
        }
        return null;
    }
}
