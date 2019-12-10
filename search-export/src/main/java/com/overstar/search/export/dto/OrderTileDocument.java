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
    private long order_no;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    private String user_id;
    private int store_id;
    private String store_name;
    private String leave_word;
    private int state;
    private BigDecimal carriage_fee;
    private String remark;
    private BigDecimal total_money;
    private BigDecimal discount_money;
    private BigDecimal real_money;
    private BigDecimal pay_money;
    private int pay_channel;
    private int coupon_id;
    private BigDecimal coupon_money;
    private LocalDateTime pay_time;
    private int promotion_id;
    private BigDecimal promotion_money;
    private int order_from;
    private LocalDateTime finished_time;
    private String comment_status;
    private LocalDateTime comment_time;
    private String ship_store;
    private String ship_no;
    private LocalDateTime ship_time;
    private long item_id;
    private long product_id;
    private String product_name;
    private long sku_id;
    private String sku_main_pic;
    private String sku_properties;
    private BigDecimal market_price;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal total_money_item;
}
