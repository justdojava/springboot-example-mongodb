package com.example.mongodb.service;

import com.example.mongodb.entity.Person;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * @author panzhi
 * @Description
 * @since 2021-01-15
 */
@Component
public class PersonService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建文档
     * @param person
     */
    public void saveUser(Person person) {
//        mongoTemplate.insert(person);
        mongoTemplate.save(person);
    }

    /**
     * 指定集合，创建文档
     * @param person
     * @param collectionName
     */
    public void saveUser(Person person, String collectionName) {
        mongoTemplate.save(person, collectionName);
    }

    /**
     * 根据用户名查询对象
     * @param userName
     * @return
     */
    
    public Person findUserByUserName(String userName) {
        Query query=new Query(Criteria.where("userName").is(userName));
        Person person =  mongoTemplate.findOne(query , Person.class);
        return person;
    }

    /**
     * 更新对象
     * @param person
     */
    
    public long updateUser(Person person) {
        Query query= new Query(Criteria.where("id").is(person.getId()));
        Update update= new Update().set("userName", person.getUserName()).set("passWord", person.getPassWord());
        //更新查询返回结果集的第一条
        UpdateResult result =mongoTemplate.updateFirst(query,update, Person.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,UserEntity.class);
        if(result!=null)
            return result.getMatchedCount();
        else
            return 0;
    }

    /**
     * 删除对象
     * @param id
     */
    
    public void deleteUserById(Long id) {
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Person.class);
    }
}
