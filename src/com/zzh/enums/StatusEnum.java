package com.zzh.enums;

/**
 * @author zzh
 * @description
 * @date
 */
public enum  StatusEnum {
    STATUSFINISH("1","已支付"),
    STATUSRETURN("2","已退货");

    private String name;
    private String value;

    StatusEnum() {
    }

    StatusEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }}
