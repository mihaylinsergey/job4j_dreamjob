package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Junior Java Job", "description_1", LocalDate.now()));
        candidates.put(2, new Candidate(2, "Middle Java Job", "description_2", LocalDate.now().plusDays(1)));
        candidates.put(3, new Candidate(3, "Senior Java Job", "description_3", LocalDate.now().minusDays(1)));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
