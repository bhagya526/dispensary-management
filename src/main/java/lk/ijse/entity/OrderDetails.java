package lk.ijse.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class OrderDetails {
    private String order_id;
    private String medicine_id;

    private String qty;
    private String unit_price;
    private String total;
}
