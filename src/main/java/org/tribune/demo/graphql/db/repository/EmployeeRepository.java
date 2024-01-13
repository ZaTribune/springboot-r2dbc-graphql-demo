package org.tribune.demo.graphql.db.repository;


import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import org.tribune.demo.graphql.db.entity.EmployeeEntity;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Repository
public interface EmployeeRepository extends R2dbcRepository<EmployeeEntity, Long> {

    @Query("select * from Employee emp left join Organization org on emp.organization = org.id where emp.age > :from and emp.age < :to order by t.name")
    Flux<EmployeeEntity> findEmployeeEntityByAgeBetween(Integer from, Integer to);

    default Flux<EmployeeEntity> filterAdvanced(Sort sort,
                                                BigDecimal salaryFrom,
                                                BigDecimal salaryTo,
                                                Integer ageFrom,
                                                Integer ageTo,
                                                String position) {
        return findEmployeeEntityBySalaryBetweenAndAgeBetweenAndPositionLikeIgnoreCase(sort, salaryFrom,
                salaryTo,
                ageFrom,
                ageTo,
                position);
    }

    Flux<EmployeeEntity> findEmployeeEntityBySalaryBetweenAndAgeBetweenAndPositionLikeIgnoreCase(Sort sort,
                                                                                                 BigDecimal salaryFrom,
                                                                                                 BigDecimal salaryTo,
                                                                                                 Integer ageFrom,
                                                                                                 Integer ageTo,
                                                                                                 String position);

}
