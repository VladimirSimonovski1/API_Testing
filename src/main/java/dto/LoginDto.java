package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginDto {

    private String email;
    private String password;

    public static LoginDto loginPayload = LoginDto.builder()
            .email("eve.holt@reqres.in")
            .password("123456789")
            .build();
}
