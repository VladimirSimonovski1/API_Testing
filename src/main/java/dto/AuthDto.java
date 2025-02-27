package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {

    private String username;
    private String password;

    public static AuthDto authRequest = AuthDto
            .builder()
            .username("emilys")
            .password("emilyspass")
            .build();
}
