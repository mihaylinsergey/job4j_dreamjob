package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateDbStore;
import ru.job4j.dreamjob.store.CandidateStore;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@ThreadSafe
@Service
public class CandidateService {
    private final CandidateDbStore store;

    private final CityService service;

    public CandidateService(CandidateDbStore store, CityService service) {
        this.store = store;
        this.service = service;
    }

    public Collection<Candidate> findAll() {
        Collection<Candidate> candidates = store.findAll();
        candidates.forEach(
                candidate -> candidate.setCity(
                        service.findById(candidate.getCity().getId())
                )
        );
        return candidates;
    }

    public void add(Candidate candidate) {
        candidate.setCreated(LocalDate.now());
        store.add(candidate);
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }
}
