package store.product;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Builder
@Data @Accessors(fluent = true)
public class Product implements Serializable{

    private String id;
    private String name;
    private Float price;
    private String unit;

}