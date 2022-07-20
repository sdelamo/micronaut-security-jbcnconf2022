package example;

import example.entities.Role;
import example.entities.User;

;
import io.micronaut.transaction.annotation.TransactionalAdvice;
import io.micronaut.validation.Validated;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import jakarta.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Validated
public class UserRepositoryImpl implements UserRepository {
    public static final String USERNAME_PARAM = "username";
    @PersistenceContext
    private EntityManager entityManager;

    private PasswordEncoder passwordEncoder;

    public UserRepositoryImpl(EntityManager entityManager,
                              PasswordEncoder passwordEncoder) {
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    @TransactionalAdvice
    @Override
    public User save(@NonNull @NotNull String username, @NonNull @NotNull String rawPassword) {
        String password = passwordEncoder.encode(rawPassword);
        User entity = new User(username, password);
        getEntityManager().persist(entity);
        return entity;
    }

    @TransactionalAdvice
    @Override
    @Nullable
    public User findByUsername(@NonNull @NotNull String username) {
        return entityManager.find(User.class, username);
    }

    @TransactionalAdvice
    @Override
    @NonNull
    public Number count() {
        final String qlString = "select count(u.username) from User u";
        return (Number) getEntityManager().createQuery(qlString).getSingleResult();
    }

    @TransactionalAdvice
    @Override
    public void delete(@NonNull @NotNull String username) {
        User user = findByUsername(username);
        if (user != null) {
            getEntityManager().remove(user);
        }
    }

    @Override
    @TransactionalAdvice
    @NonNull
    public List<String> findRolesByUsername(@NonNull @NotNull String username) {
        final String qlString = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username";
        User u = entityManager.createQuery(qlString, User.class)
                .setParameter(USERNAME_PARAM, username)
                .getSingleResult();
        if (u != null) {
            return u.getRoles().stream().map(Role::getAuthority).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @TransactionalAdvice
    public void addUserRole(@NonNull @NotNull String username, @NonNull @NotNull Role role) {
        User user = findByUsername(username);
        if (user != null) {
            user.addRole(role);
            getEntityManager().persist(user);
        }
    }

}
