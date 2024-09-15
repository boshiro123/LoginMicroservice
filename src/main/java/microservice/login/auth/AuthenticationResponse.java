package microservice.login.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record AuthenticationResponse(

        @JsonProperty("token")
        String token
//        @JsonProperty("access_token")
//        String accessToken,

//        @JsonProperty("refresh_token")
//        String refreshToken

) {
}
