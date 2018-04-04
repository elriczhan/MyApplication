package com.example.elric.myapplication.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DaoBean {
    @Id(autoincrement = true)
    private Long aid;

    private String name;
    @Property(nameInDb = "AGES")
    private String age;

    @Keep
    public DaoBean(Long aid, String name, String age) {
        this.aid = aid;
        this.name = name;
        this.age = age;
    }

    @Keep
    public DaoBean() {
    }

    public long getId() {
        return aid;
    }

    public void setId(Long aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Long getAid() {
        return this.aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

}
