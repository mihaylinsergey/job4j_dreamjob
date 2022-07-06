package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final Map<AtomicInteger, Candidate> candidates = new ConcurrentHashMap<AtomicInteger, Candidate>();
    private AtomicInteger count;

    private CandidateStore() {
        candidates.put(new AtomicInteger(1), new Candidate(1, "Junior Java Job", "description_1", LocalDate.now()));
        candidates.put(new AtomicInteger(2), new Candidate(2, "Middle Java Job", "description_2", LocalDate.now().plusDays(1)));
        candidates.put(new AtomicInteger(3), new Candidate(3, "Senior Java Job", "description_3", LocalDate.now().minusDays(1)));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        candidates.putIfAbsent(count, candidate);
        count.getAndIncrement();
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }
}
