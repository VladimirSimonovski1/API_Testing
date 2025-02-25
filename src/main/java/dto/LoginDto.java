package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginDto {

    private String email;
    private String password;

    public static LoginDto loginRequest = LoginDto.builder()
            .email("eve.holt@reqres.in")
            .password("123456789")
            .build();
}
