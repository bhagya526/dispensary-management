package lk.ijse.Dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PatientTm {
    private String patId;
    private String patName;
    private String Address;
    private String TelNo;
    private String Email;
}
