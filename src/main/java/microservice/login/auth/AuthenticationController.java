package microservice.login.auth;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import microservice.login.dto.request.CreateUserRequest;
import microservice.login.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse register(
            @RequestBody @Valid CreateUserRequest request
    ){
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return authenticationService.authenticate(request);
    }

}
