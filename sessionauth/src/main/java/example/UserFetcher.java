package example;

public interface UserFetcher {
    UserState findByUsername(String username);
}
