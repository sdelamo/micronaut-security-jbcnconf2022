package example;

import example.entities.Role;
import example.entities.User;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserRepository {

    User save(@NotBlank @NonNull String username, @NotBlank @NonNull String rawPassword);

    @Nullable
    User findByUsername(@NotBlank @NonNull String username);

    @NonNull
    Number count();

    void delete(@NotBlank @NonNull String username);

    @NonNull
    List<String> findRolesByUsername(@NonNull @NotBlank String username);

    void addUserRole(@NonNull @NotBlank String username, @NonNull @NotNull Role role);
}
