package org.tribune.demo.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * The audit annotations like {@link CreatedDate} or {@link LastModifiedDate} are also supported.
 * To enable the auditing feature, we need to declare it explicitly
 *
 **/
@EnableR2dbcRepositories
@EnableR2dbcAuditing
@SpringBootApplication
public class GraphqlDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlDemoApplication.class, args);
    }

}
