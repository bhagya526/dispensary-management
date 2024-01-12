package lk.ijse.Dto;

import lk.ijse.Dto.tm.CartTm;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class PlaceOrderDto {
    private String orderId;
    private String date;
    private String pres_Id;
    private double total_price;
    private String type;
    private String medID;
    private List<CartTm> cartTmList = new ArrayList<>();
}
