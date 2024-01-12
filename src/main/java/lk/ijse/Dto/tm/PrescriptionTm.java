package lk.ijse.Dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class PrescriptionTm {
    private String Pres_id;
    private String Date;
    private String Description;
    private String Cust_id;
}
