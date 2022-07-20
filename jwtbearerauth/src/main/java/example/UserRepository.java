package example;

import example.entities.Role;
import example.entities.User;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserRepository {

    User save(@NotNull @NonNull String username, @NotNull @NonNull String rawPassword);

    @Nullable
    User findByUsername(@NotNull @NonNull String username);

    @NonNull
    Number count();

    void delete(@NotNull @NonNull String username);

    @NonNull
    List<String> findRolesByUsername(@NonNull @NotNull String username);

    void addUserRole(@NonNull @NotNull String username, @NonNull @NotNull Role role);
}
