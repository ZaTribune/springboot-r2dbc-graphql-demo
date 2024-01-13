package org.tribune.demo.graphql.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.tribune.demo.graphql.codegen.types.Employee;
import org.tribune.demo.graphql.db.entity.EmployeeEntity;

@Component
@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(source = "department",target = "department.id")
    @Mapping(source = "organization",target = "organization.id")
    Employee toDto(EmployeeEntity entity);
}
