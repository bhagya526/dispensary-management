package lk.ijse.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CustomerDto {

    private String cus_id;
    private String name;

    private String email;

    private String address;
    private String tel;
}
