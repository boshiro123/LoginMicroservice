package microservice.login.service.impl;

import lombok.RequiredArgsConstructor;
import microservice.login.config.SecurityUser;
import microservice.login.error.ErrorMessages;
import microservice.login.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecurityUser(
                userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_MESSAGE, username)))
        );
    }
}
