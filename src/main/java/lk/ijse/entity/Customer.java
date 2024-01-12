package lk.ijse.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {

    private String cus_id;
    private String name;

    private String email;

    private String address;
    private String tel;


}
