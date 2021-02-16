package com.example.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author panzhi
 * @Description
 * @since 2021-01-15
 */

/**
 * 使用@Document注解指定集合名称
 */
@Document(collection="persons")
public class Person implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;

    /**
     * 使用注解指定MongoDB中的 _id 主键
     */
    @Id
    private Long id;

    @Indexed
    private String userName;

    private String passWord;

    private Integer age;

    /**
     * 创建一个5秒之后文档自动删除的索引
     */
    @Indexed(expireAfterSeconds=5)
    private Date createTime;

    public Long getId() {
        return id;
    }

    public Person setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Person setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public Person setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Person setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Person setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                '}';
    }
}
