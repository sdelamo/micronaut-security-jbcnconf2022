package example;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Singleton
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AppConfiguration appConfiguration;

    public CustomAuthenticationProvider(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Mono.<AuthenticationResponse>create(emitter -> {
            if (authenticationRequest.getIdentity().equals(appConfiguration.getUsername()) &&
                    authenticationRequest.getSecret().equals(appConfiguration.getPassword())
            ) {
                emitter.success(AuthenticationResponse.success(authenticationRequest.getIdentity().toString()));
            } else{
                emitter.error(AuthenticationResponse.exception());
            }
        });
    }
}
