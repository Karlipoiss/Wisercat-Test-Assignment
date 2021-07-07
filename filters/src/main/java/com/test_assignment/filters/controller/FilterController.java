package com.test_assignment.filters.controller;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import com.test_assignment.filters.model.Filter;
import com.test_assignment.filters.repository.FilterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/filters")
public class FilterController {

    private final FilterRepo filterRepo;

    @Autowired
    public FilterController(FilterRepo clientRepository) {
        this.filterRepo = clientRepository;
    }

    @GetMapping
    public List<Filter> getFilters() {
        return (List<Filter>) filterRepo.findAll();
    }

    @GetMapping("/{id}")
    public Filter getFilter(@PathVariable Long id) {
        return filterRepo.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public ResponseEntity createFilter(@Valid @RequestBody Filter filter) throws URISyntaxException {
        Filter savedFilter = filterRepo.save(filter);
        return ResponseEntity.created(new URI("/filters/" + savedFilter.getId())).body(savedFilter);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateFilter(@PathVariable Long id, @Valid @RequestBody Filter filter) {
        Filter currentFilter = filterRepo.findById(id).orElseThrow(RuntimeException::new);
        currentFilter.setName(filter.getName());
        currentFilter.setCriteriaList(filter.getCriteriaList());
        currentFilter = filterRepo.save(filter);

        return ResponseEntity.ok(currentFilter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(@PathVariable Long id) {
        filterRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, List<String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        List<String> errorMessages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errorMessages.add(error.getDefaultMessage());
        });
        errors.put("error", errorMessages);
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, List<String>> handleConstraintExceptions(ConstraintViolationException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        List<String> errorMessages = new ArrayList<>();
        ex.getConstraintViolations().forEach((error) -> {
            errorMessages.add(error.getMessage());
        });
        errors.put("error", errorMessages);
        return errors;
    }
}