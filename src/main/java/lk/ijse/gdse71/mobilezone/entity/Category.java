package lk.ijse.gdse71.mobilezone.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category{
    private String categoryId;
    private String categoryName;
    private String subCategory;
    private String description;
}
