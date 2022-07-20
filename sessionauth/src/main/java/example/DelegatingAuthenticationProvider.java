package example;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.security.authentication.AuthenticationException;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.ExecutorService;

import static io.micronaut.security.authentication.AuthenticationFailureReason.*;

@Requires(beans = AuthoritiesFetcher.class)
@Requires(beans = PasswordEncoder.class)
@Requires(beans = UserFetcher.class)
@Singleton
class DelegatingAuthenticationProvider implements AuthenticationProvider {

    private final UserFetcher userFetcher;
    private final PasswordEncoder passwordEncoder;
    private final AuthoritiesFetcher authoritiesFetcher;
    private final Scheduler scheduler;

    DelegatingAuthenticationProvider(UserFetcher userFetcher,
                                     PasswordEncoder passwordEncoder,
                                     AuthoritiesFetcher authoritiesFetcher,
                                     @Named(TaskExecutors.IO) ExecutorService executorService) {
        this.userFetcher = userFetcher;
        this.passwordEncoder = passwordEncoder;
        this.authoritiesFetcher = authoritiesFetcher;
        this.scheduler = Schedulers.fromExecutorService(executorService);
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest,
                                                          AuthenticationRequest<?, ?> authenticationRequest) {
        return Mono.<AuthenticationResponse>create(emitter -> {
            UserState user = fetchUserState(authenticationRequest);
            AuthenticationFailed authenticationFailed = validate(user, authenticationRequest);
            if (authenticationFailed != null) {
                emitter.error(new AuthenticationException(authenticationFailed));
            } else {
                emitter.success(createSuccessfulAuthenticationResponse(user));
            }
        }).subscribeOn(scheduler);
    }

    private AuthenticationFailed validate(UserState user, AuthenticationRequest authenticationRequest) {

        AuthenticationFailed authenticationFailed = null;
        if (user == null) {
            authenticationFailed = new AuthenticationFailed(USER_NOT_FOUND);

        } else if (!user.isEnabled()) {
            authenticationFailed = new AuthenticationFailed(USER_DISABLED);

        } else if (user.isAccountExpired()) {
            authenticationFailed = new AuthenticationFailed(ACCOUNT_EXPIRED);

        } else if (user.isAccountLocked()) {
            authenticationFailed = new AuthenticationFailed(ACCOUNT_LOCKED);

        } else if (user.isPasswordExpired()) {
            authenticationFailed = new AuthenticationFailed(PASSWORD_EXPIRED);

        } else if (!passwordEncoder.matches(authenticationRequest.getSecret().toString(), user.getPassword())) {
            authenticationFailed = new AuthenticationFailed(CREDENTIALS_DO_NOT_MATCH);
        }

        return authenticationFailed;
    }

    private UserState fetchUserState(AuthenticationRequest authRequest) {
        final String username = authRequest.getIdentity().toString();
        return userFetcher.findByUsername(username);
    }

    private AuthenticationResponse createSuccessfulAuthenticationResponse(UserState user) {
        List<String> authorities = authoritiesFetcher.findAuthoritiesByUsername(user.getUsername());
        return AuthenticationResponse.success(user.getUsername(), authorities);
    }
}
