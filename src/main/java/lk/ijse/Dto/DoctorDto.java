package lk.ijse.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DoctorDto {
    private String doc_id;
    private String name;
    private String email;
    private  int contact_no;
}
