package example;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("app")
public interface AppConfiguration {

    String getUsername();

    String getPassword();
}
