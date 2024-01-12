package lk.ijse.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class Doctor {

    private String doc_id;
    private String name;
    private String email;
    private int contact_no;

}
