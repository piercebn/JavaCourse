package com.example.mysql.appmysql.entity;

import java.io.Serializable;

/**
 * (T1)实体类
 *
 * @author makejava
 * @since 2021-09-21 21:25:15
 */
public class T1 implements Serializable {
    private static final long serialVersionUID = -95824170884067130L;
    
    private Integer id;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

