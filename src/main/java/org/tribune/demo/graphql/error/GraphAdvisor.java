package org.tribune.demo.graphql.error;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class GraphAdvisor extends DataFetcherExceptionResolverAdapter {


    @Override
    protected GraphQLError resolveToSingleError(@NonNull Throwable e, @NonNull DataFetchingEnvironment dev) {
        log.error("error:",e);

        ErrorType type;
        if (e instanceof DataIntegrityViolationException) {
            type = ErrorType.BAD_REQUEST;
        } else
            type = ErrorType.INTERNAL_ERROR;

        return GraphqlErrorBuilder.newError(dev)
                .message("Received Message: %s", e.getMessage())
                .errorType(type)
                .build();
    }
}
