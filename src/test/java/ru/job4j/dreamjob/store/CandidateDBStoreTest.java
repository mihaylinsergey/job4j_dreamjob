package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import java.time.LocalDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDBStoreTest {

    @Test
    public void whenCreateCandidate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        City city = new City();
        Candidate candidate = new Candidate(0, "Java Developer", city);
        System.out.println(store);
        System.out.println(candidate);
        candidate.setCreated(LocalDate.now());
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenUpdatePost() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        City city = new City();
        Candidate candidate = new Candidate(0, "Java Developer", city);
        System.out.println(store);
        System.out.println(candidate);
        candidate.setCreated(LocalDate.now());
        store.add(candidate);
        candidate.setName("Update Java Developer");
        store.update(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }
}
