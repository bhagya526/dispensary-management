package lk.ijse.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderDto {
    private String order_id;
    private String date;
    private String pres_id;
    private double total_price;


}
