package lk.ijse.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class Prescription {
    private String pres_id;
    private String date;
    private String description;
    private String cust_id;
}
