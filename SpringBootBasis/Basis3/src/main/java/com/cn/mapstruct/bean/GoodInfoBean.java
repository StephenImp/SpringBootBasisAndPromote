package com.cn.mapstruct.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "good_infos")
@Data
public class GoodInfoBean
{
    @Id
    @Column(name = "tg_id")
    private Long id;
    @Column(name = "tg_title")
    private String title;
    @Column(name = "tg_price")
    private double price;
    @Column(name = "tg_order")
    private int order;
    @Column(name = "tg_type_id")
    private Long typeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
