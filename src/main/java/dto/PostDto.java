package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDto {

    private String title;
    private String body;
    private int userId;

    public static PostDto postBody = PostDto.builder()
            .title("Title")
            .body("Body")
            .userId(77)
            .build();
}
