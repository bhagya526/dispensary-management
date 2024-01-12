package lk.ijse.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class PrescriptionDto {
    private String pres_id;

    private String date;

    private String description;

    private String cust_id;
}
