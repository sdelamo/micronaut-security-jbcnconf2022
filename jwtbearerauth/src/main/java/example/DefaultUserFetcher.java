package example;

import jakarta.inject.Singleton;

@Singleton
public class DefaultUserFetcher implements UserFetcher {

    private final UserRepository userRepository;

    public DefaultUserFetcher(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserState findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
