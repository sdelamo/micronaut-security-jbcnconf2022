package example;

import example.entities.Role;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import javax.validation.constraints.NotNull;

public interface RoleRepository {
    Role save(@NotNull @NonNull String authority);

    @NonNull
    Number count();

    @Nullable
    Role findByAuthority(@NonNull @NotNull String authority);

    void delete(@NonNull @NotNull String authority);
}
