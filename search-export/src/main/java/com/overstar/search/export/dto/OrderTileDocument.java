package com.overstar.search.export.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/12/10 19:05
 */
@Data
public class OrderTileDocument {
    private long orderNo;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private int userId;
    private int storeId;
    private String storeName;
    private String leaveWord;
    private Byte state;
    private BigDecimal carriageFee;
    private String remark;
    private BigDecimal totalMoney;
    private BigDecimal discountMoney;
    private BigDecimal realMoney;
    private BigDecimal payMoney;
    private Byte payChannel;
    private Long couponId;
    private BigDecimal couponMoney;
    private LocalDateTime payTime;
    private Integer promotionId;
    private BigDecimal promotionMoney;
    private Byte orderFrom;
    private LocalDateTime finishedTime;
    private Byte commentStatus;
    private LocalDateTime commentTime;
    private Long shipStore;
    private String shipNo;
    private LocalDateTime shipTime;
    private String digest;
    private long itemId;
    private long productId;
    private String productName;
    private long skuId;
    private String skuMainPic;
    private String skuProperties;
    private BigDecimal marketPrice;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal totalMoneyItem;
    //子订单或者主订单 0 主订单 1子订单
    private Byte orderType;

    private LocalDateTime docCreateTime;
    private Byte docVersion;
}
