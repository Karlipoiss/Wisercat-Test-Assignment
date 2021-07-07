package com.test_assignment.filters.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Filter {
    private @Id @GeneratedValue Long id;
    @NotEmpty(message = "Filter must have a name")
    private String name;

    @OneToMany(
            targetEntity= Criteria.class,
            cascade = CascadeType.ALL)
    @NotEmpty(message = "One criteria is required")
    private List<Criteria> criteriaList;

    public Filter() {}

    public Filter(String name, List<Criteria> criteriaList) {
        this.name = name;
        this.criteriaList = criteriaList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Criteria> getCriteriaList() {
        return criteriaList;
    }

    public void setCriteriaList(List<Criteria> criteriaList) {
        this.criteriaList = criteriaList;
    }
}
