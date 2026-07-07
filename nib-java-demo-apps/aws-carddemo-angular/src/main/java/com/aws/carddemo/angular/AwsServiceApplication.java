package com.aws.carddemo.angular;

import com.nib.angular.service.ServiceApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
        basePackages = {
                "com.aws.carddemo.angular"
        }
)
public class AwsServiceApplication extends ServiceApplication {

    /**
     * Used when run as JAR
     */
    public static void main(String[] args) {
        SpringApplication.run(AwsServiceApplication.class, args);
    }

    /**
     * Used when run as WAR
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AwsServiceApplication.class);
    }
}
