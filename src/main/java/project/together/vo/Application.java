package project.together.vo;

import lombok.Data;

@Data
public class Application {
    private String serId; // column 에서는 app_ser_id
    private Integer volId; // column 에서는 app_vol_id
    private Servant servant;
}
