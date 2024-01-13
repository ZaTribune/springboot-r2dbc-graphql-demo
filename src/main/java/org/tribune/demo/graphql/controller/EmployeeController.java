package org.tribune.demo.graphql.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tribune.demo.graphql.codegen.types.Employee;
import org.tribune.demo.graphql.codegen.types.EmployeeFilter;
import org.tribune.demo.graphql.codegen.types.EmployeeInput;
import org.tribune.demo.graphql.codegen.types.FilterField;
import org.tribune.demo.graphql.db.entity.EmployeeEntity;
import org.tribune.demo.graphql.db.repository.EmployeeRepository;
import org.tribune.demo.graphql.mapper.EmployeeInputMapper;
import org.tribune.demo.graphql.mapper.EmployeeMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
public class EmployeeController {

    private final EmployeeRepository repository;
    private final EmployeeMapper employeeMapper;
    private final EmployeeInputMapper employeeInputMapper;


    @QueryMapping
    public Flux<Employee> getAll() {
        log.info("Getting all employees.");
        return repository.findAll()
                .map(employeeMapper::toDto);
    }

    @QueryMapping
    public Mono<Employee> findById(@Argument("id") Long id) {
        log.info("Getting employee by id: {}", id);
        return repository.findById(id)
                .map(employeeMapper::toDto);
    }


    @QueryMapping
    public Flux<Employee> findWithFilter(@Argument("filter") EmployeeFilter filter) {


        FilterField filterField;
        List<Sort> sorts = new ArrayList<>();
        BigDecimal salaryFrom = new BigDecimal(0);
        BigDecimal salaryTo = new BigDecimal(Long.MAX_VALUE);

        int ageFrom = 0;
        int ageTo = Integer.MAX_VALUE;

        String position = "";

        if (filter.getSalary() != null) {
            sorts.add(Sort.by("salary"));
            filterField = filter.getSalary();
            switch (filterField.getOperator()) {
                case ">":
                    salaryFrom = new BigDecimal(filterField.getValue());
                    break;
                case "<":
                    salaryTo = new BigDecimal(filterField.getValue());
                    break;
                default:
                case "=":
                    salaryFrom = new BigDecimal(filterField.getValue());
                    salaryTo = new BigDecimal(filterField.getValue());
            }
        }
        if (filter.getAge() != null) {
            sorts.add(Sort.by("age"));
            filterField = filter.getAge();
            switch (filterField.getOperator()) {
                case ">":
                    ageFrom = Integer.parseInt(filterField.getValue());
                    break;
                case "<":
                    ageTo = Integer.parseInt(filterField.getValue());
                    break;
                default:
                case "=":
                    ageFrom = Integer.parseInt(filterField.getValue());
                    ageTo = Integer.parseInt(filterField.getValue());
            }
        }
        if (filter.getPosition() != null) {
            sorts.add(Sort.by("position"));
            filterField = filter.getPosition();
            position = filterField.getValue();
        }

        Sort input = Sort.unsorted();
        for (Sort s : sorts) {
            input = input.and(s);
        }

        return repository.filterAdvanced(input,
                        salaryFrom,
                        salaryTo,
                        ageFrom,
                        ageTo,
                        position.toLowerCase().concat("%"))
                .map(employeeMapper::toDto);

        //return repository.findAll(input).map(employeeMapper::toDto);
    }

    @MutationMapping
    public Mono<Employee> newEmployee(@Argument EmployeeInput employee) {
        log.info("Adding a new Employee");

        EmployeeEntity entity = employeeInputMapper.toEntity(employee);
        return repository.save(entity).map(employeeMapper::toDto);
    }

}
