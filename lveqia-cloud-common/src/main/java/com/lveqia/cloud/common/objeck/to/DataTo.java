package com.lveqia.cloud.common.objeck.to;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 封装超时扣费的业务传输对象
 */
@Data
public class DataTo implements Serializable {
    private long did;
    private Date date;
    private Integer status;

}
