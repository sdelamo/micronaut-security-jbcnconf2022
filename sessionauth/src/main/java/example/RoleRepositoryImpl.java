package example;

import example.entities.Role;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.validation.Validated;
import jakarta.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import io.micronaut.transaction.annotation.TransactionalAdvice;
import javax.validation.constraints.NotNull;

@Singleton
@Validated
public class RoleRepositoryImpl implements RoleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public RoleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    @TransactionalAdvice
    @Override
    public Role save(@NonNull @NotNull String authority) {
        Role role = new Role();
        role.setAuthority(authority);
        getEntityManager().persist(role);
        return role;
    }

    @TransactionalAdvice
    @NonNull
    @Override
    public Number count() {
        final String qlString = "select count(r.authority) from Role r";
        return (Number) getEntityManager().createQuery(qlString).getSingleResult();
    }

    @TransactionalAdvice
    @Override
    @Nullable
    public Role findByAuthority(@NonNull @NotNull String authority) {
        return entityManager.find(Role.class, authority);
    }

    @TransactionalAdvice
    @Override
    public void delete(@NonNull @NotNull String authority) {
        Role role = findByAuthority(authority);
        if (role != null) {
            getEntityManager().remove(role);
        }
    }

}
