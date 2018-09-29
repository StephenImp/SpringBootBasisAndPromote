package com.cn.quartz.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "basic_good_info")
@Data
public class GoodInfoEntity {
    /**
     * 商品编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BGI_ID")
    private Long id;
    /**
     * 商品名称
     */
    @Column(name = "BGI_NAME")
    private String name;
    /**
     * 商品单位
     */
    @Column(name = "BGI_UNIT")
    private String unit;
    /**
     * 商品单价
     */
    @Column(name = "BGI_PRICE")
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
