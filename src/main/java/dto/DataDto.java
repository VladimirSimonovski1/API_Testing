package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataDto {

    private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
}
