package Bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Order {
    private Integer orderid;
    private Integer shopid;
    private Date buytime;
    private String address;
    private Goods goods;
}
