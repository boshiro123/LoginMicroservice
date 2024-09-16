package microservice.login.auth;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import microservice.login.config.SecurityUser;
import microservice.login.dto.request.CreateUserRequest;
import microservice.login.entity.Role;
import microservice.login.entity.RoleName;
import microservice.login.entity.User;
import microservice.login.error.ErrorMessages;
import microservice.login.error.ResourceNotFoundException;
import microservice.login.repositories.RoleRepository;
import microservice.login.repositories.UserRepository;
import microservice.login.service.AuthenticationService;
import microservice.login.service.JwtService;
import microservice.login.token.Token;
import microservice.login.token.TokenRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class  AuthenticationServiceImpl implements AuthenticationService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(CreateUserRequest request) {
        Role defaultRole = findRoleByName(RoleName.USER);
        UserDetails userDetails = buildUser(request, defaultRole);
        User savedUser = userRepository.save(((SecurityUser)userDetails).getUser());
        String token = jwtService.generateToken(userDetails);

        saveUserToken(savedUser, token);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = findUserByEmail(request.email());
        UserDetails userDetails = new SecurityUser(user);
        String token = jwtService.generateToken(userDetails);

        revokeAllUserTokens(user);

        saveUserToken(user, token);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
    private void saveUserToken(User user, String accessToken) {
        Token token = Token.builder()
                .user(user)
                .token(accessToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_MESSAGE, email)));
    }
    private Role findRoleByName(RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.ROLE_NOT_FOUND_MESSAGE, roleName)));
    }

    private UserDetails buildUser(CreateUserRequest request, Role role) {
        User user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .build();
        return new SecurityUser(user);
    }


}
