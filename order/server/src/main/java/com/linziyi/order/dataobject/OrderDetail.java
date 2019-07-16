package com.linziyi.order.dataobject;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class OrderDetail {
    @Id
    private String detailId;
    //订单ID
    private String orderId;
    //产品ID
    private String productId;
    //商品名称
    private String productName;
    //当前价格,单位分
    private BigDecimal productPrice;
    //数量
    private Integer productQuantity;
    //图标
    private String productIcon;
    private Date createTime;
    private Date updateTime;
}
