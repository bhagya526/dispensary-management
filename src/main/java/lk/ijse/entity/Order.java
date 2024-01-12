package lk.ijse.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class Order {
    private String order_id;
    private String date;
    private String pres_id;
    private double total_price;

}
