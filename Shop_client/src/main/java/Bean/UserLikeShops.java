package Bean;

import lombok.Data;

import java.util.Date;

@Data
public class UserLikeShops {
    private String username;
    private Integer likeshopid;
    private Date addtime;
}
