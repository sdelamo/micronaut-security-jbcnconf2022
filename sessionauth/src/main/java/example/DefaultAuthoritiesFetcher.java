package example;

import io.micronaut.core.annotation.NonNull;
import org.reactivestreams.Publisher;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Singleton
public class DefaultAuthoritiesFetcher implements AuthoritiesFetcher {

    private final UserRepository userRepository;

    public DefaultAuthoritiesFetcher(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @NonNull
    public List<String> findAuthoritiesByUsername(@NonNull @NotBlank String username) {
        return userRepository.findRolesByUsername(username);
    }
}
