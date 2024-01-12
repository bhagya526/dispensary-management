package lk.ijse.Dto;

import lombok.*;

import java.util.PrimitiveIterator;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MedicineDto {
    private String medicine_id;

    private String name;

    private String type;

    private String qty;
    private String price;
}
