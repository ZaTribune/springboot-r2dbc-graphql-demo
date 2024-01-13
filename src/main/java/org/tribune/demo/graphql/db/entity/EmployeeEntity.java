package org.tribune.demo.graphql.db.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Table(name = "EMPLOYEE")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class EmployeeEntity {

    @Id
    private Long id;
    /*
    The @Version annotation is fully supported, and it comes with an optimistic locking mechanism.
    Each time a record is about to be saved, the actual version of the record is compared to the provided one,
    then if they are identical, the version is incremented and the record is saved.
    If they are different, the record will not be saved and an error would return.
     */
    @Version
    private Long version;

    private String firstName;
    private String lastName;
    private String position;
    private BigDecimal salary;
    private Integer age;
    
    private Long organization;
    private Long department;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}