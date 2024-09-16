package microservice.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class DemoController {

    @GetMapping("/hello")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> refreshToken() throws IOException {
        return ResponseEntity.ok("Hello from microservice");
    }

}
