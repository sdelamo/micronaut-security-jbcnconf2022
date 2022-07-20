package example;

import io.micronaut.core.annotation.NonNull;
import javax.validation.constraints.NotBlank;
import java.util.List;

public interface AuthoritiesFetcher {
    @NonNull
    List<String> findAuthoritiesByUsername(@NonNull @NotBlank String username);
}
