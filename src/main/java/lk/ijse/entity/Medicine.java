package lk.ijse.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class Medicine {
    private String medicine_id;

    private String name;

    private String type;

    private String qty;
    private String price;
}
