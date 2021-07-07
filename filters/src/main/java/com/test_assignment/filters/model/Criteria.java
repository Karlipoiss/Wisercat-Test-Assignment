package com.test_assignment.filters.model;

import com.test_assignment.filters.model.constraints.CriteriaComparisonConstraint;
import com.test_assignment.filters.model.constraints.CriteriaValueConstraint;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@CriteriaComparisonConstraint
@CriteriaValueConstraint
public class Criteria {
    private @Id @GeneratedValue Long id;
    public enum Type {
        AMOUNT,
        TITLE,
        DATE
    }
    public enum Comparison {
        MORE,
        LESS,
        STARTS,
        ENDS,
        FROM,
        TO,
        EQUAL
    }

    @NotNull(message = "Criteria type is mandatory")
    private Type type;
    @NotNull(message = "Criteria comparison is mandatory")
    private Comparison comparison;

    private String textValue;
    private Long numberValue;
    private Date dateValue;

    public Criteria(Type type, Comparison comparison, String textValue, Long numberValue, Date dateValue) {
        this.type = type;
        this.comparison = comparison;
        this.textValue = textValue;
        this.numberValue = numberValue;
        this.dateValue = dateValue;
    }

    public Criteria() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Comparison getComparison() {
        return comparison;
    }

    public void setComparison(Comparison comparison) {
        this.comparison = comparison;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public Long getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(Long numberValue) {
        this.numberValue = numberValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }
}
