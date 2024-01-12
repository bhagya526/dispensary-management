package lk.ijse.Dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class MedicineTm {
    private String medId;
    private String medName;
    private String medType;
    private String medQty;
    private String medPrice;
}
