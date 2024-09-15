package microservice.login.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import microservice.login.auth.AuthenticationRequest;
import microservice.login.auth.AuthenticationResponse;
import microservice.login.dto.request.CreateUserRequest;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(CreateUserRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
