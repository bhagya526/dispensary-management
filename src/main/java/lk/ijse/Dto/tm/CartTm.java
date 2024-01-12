package lk.ijse.Dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartTm {

    private String medId;
    private String medName;
    private String medQty;
    private String medPrice;
    private String total;
}
