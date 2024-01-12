package lk.ijse.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderDetailsDto {
    private String order_id;
    private String medicine_id;

    private String qty;
    private String unit_price;
    private String total;
}
