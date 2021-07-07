package com.test_assignment.filters;

import com.test_assignment.filters.model.Criteria;
import com.test_assignment.filters.model.Filter;
import com.test_assignment.filters.repository.FilterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final FilterRepo repository;

    @Autowired
    public DatabaseLoader(FilterRepo repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {
        List<Criteria> criterias = Collections.singletonList(new Criteria(Criteria.Type.AMOUNT, Criteria.Comparison.MORE, null, 4L, null));
        List<Criteria> criterias2 = Collections.singletonList(new Criteria(Criteria.Type.TITLE, Criteria.Comparison.STARTS, "Pealkiri", null, null));
        this.repository.save(new Filter("Test filter", criterias));
        this.repository.save(new Filter("Test filter2", criterias2));
    }
}