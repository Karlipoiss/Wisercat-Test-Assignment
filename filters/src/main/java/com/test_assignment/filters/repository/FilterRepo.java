package com.test_assignment.filters.repository;

import com.test_assignment.filters.model.Filter;
import org.springframework.data.repository.CrudRepository;

public interface FilterRepo extends CrudRepository<Filter, Long> {

}
