package com.cn.scheduled.entity;

import lombok.Data;

import java.util.List;

@Data
public class CommonData<T> {
    private List<T> list;
}
