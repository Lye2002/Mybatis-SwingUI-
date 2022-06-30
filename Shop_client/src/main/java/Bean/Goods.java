package Bean;
import lombok.Data;

import java.util.Date;

@Data
public class Goods {
    private String id;
    private String name;
    private float price;
    private String img;
    private Date firsttime;
    private Date updatetime;
    private String content;
    private String sellerid;
}
