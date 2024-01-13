package org.tribune.demo.graphql.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.tribune.demo.graphql.codegen.types.EmployeeInput;
import org.tribune.demo.graphql.db.entity.EmployeeEntity;

@Component
@Mapper(componentModel = "spring")
public interface EmployeeInputMapper {
    EmployeeInputMapper INSTANCE = Mappers.getMapper(EmployeeInputMapper.class);

    EmployeeEntity toEntity(EmployeeInput input);
}