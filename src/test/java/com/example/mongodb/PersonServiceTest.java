package com.example.mongodb;

import com.alibaba.fastjson.JSON;
import com.example.mongodb.service.PersonService;
import com.example.mongodb.entity.Person;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author panzhi
 * @Description
 * @since 2021-01-15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入文档
     * @throws Exception
     */
    @Test
    public void insert() throws Exception {
        Person person =new Person();
        person.setId(1l);
        person.setUserName("张三");
        person.setPassWord("123456");
        person.setCreateTime(new Date());
        mongoTemplate.insert(person);
    }

    /**
     * 自定义集合，插入文档
     * @throws Exception
     */
    @Test
    public void insertCustomCollection() throws Exception {
        Person person =new Person();
        person.setId(1l);
        person.setUserName("张三");
        person.setPassWord("123456");
        person.setCreateTime(new Date());
        mongoTemplate.insert(person, "custom_person");
    }

    /**
     * 自定义集合，批量插入文档
     * @throws Exception
     */
    @Test
    public void insertBatch() throws Exception {
        List<Person> personList = new ArrayList<>();
        Person person1 =new Person();
        person1.setId(10l);
        person1.setUserName("张三");
        person1.setPassWord("123456");
        person1.setCreateTime(new Date());
        personList.add(person1);

        Person person2 =new Person();
        person2.setId(11l);
        person2.setUserName("李四");
        person2.setPassWord("123456");
        person2.setCreateTime(new Date());
        personList.add(person2);

        mongoTemplate.insert(personList, "custom_person");
    }

    /**
     * 存储文档，如果没有插入，否则更新
     * @throws Exception
     */
    @Test
    public void save() throws Exception {
        Person person =new Person();
        person.setId(13l);
        person.setUserName("八八");
        person.setPassWord("123456");
        person.setAge(40);
        person.setCreateTime(new Date());
        mongoTemplate.save(person);
    }

    /**
     * 自定义集合，存储文档
     * @throws Exception
     */
    @Test
    public void saveCustomCollection() throws Exception {
        Person person =new Person();
        person.setId(1l);
        person.setUserName("张三");
        person.setPassWord("123456");
        person.setCreateTime(new Date());
        mongoTemplate.save(person, "custom_person");
    }


    /**
     * 更新文档，匹配查询到的文档数据中的第一条数据
     * @throws Exception
     */
    @Test
    public void updateFirst() throws Exception {
        //更新对象
        Person person =new Person();
        person.setId(1l);
        person.setUserName("张三123");
        person.setPassWord("123456");
        person.setCreateTime(new Date());

        //更新条件
        Query query= new Query(Criteria.where("id").is(person.getId()));

        //更新值
        Update update= new Update().set("userName", person.getUserName()).set("passWord", person.getPassWord());
        //更新查询满足条件的文档数据（第一条）
        UpdateResult result =mongoTemplate.updateFirst(query,update, Person.class);
        if(result!=null){
            System.out.println("更新条数：" + result.getMatchedCount());
        }
    }

    /**
     * 更新文档，匹配查询到的文档数据中的所有数据
     * @throws Exception
     */
    @Test
    public void updateMany() throws Exception {
        //更新对象
        Person person =new Person();
        person.setId(1l);
        person.setUserName("张三");
        person.setPassWord("123456");
        person.setCreateTime(new Date());

        //更新条件
        Query query= new Query(Criteria.where("id").is(person.getId()));

        //更新值
        Update update= new Update().set("userName", person.getUserName()).set("passWord", person.getPassWord());
        //更新查询满足条件的文档数据（全部）
        UpdateResult result = mongoTemplate.updateMulti(query, update, Person.class);
        if(result!=null){
            System.out.println("更新条数：" + result.getMatchedCount());
        }
    }

    /**
     * 删除符合条件的所有文档
     * @throws Exception
     */
    @Test
    public void remove() throws Exception {
        Person person =new Person();
        person.setId(1l);
        person.setUserName("张三");
        person.setPassWord("123456");
        person.setCreateTime(new Date());

        Query query = new Query(Criteria.where("userName").is(person.getUserName()));
        DeleteResult result = mongoTemplate.remove(query, Person.class);
        System.out.println("删除条数：" + result.getDeletedCount());
    }

    /**
     * 删除符合条件的单个文档，并返回删除的文档
     * @throws Exception
     */
    @Test
    public void findAndRemove() throws Exception {
        Person person =new Person();
        person.setId(1l);
        person.setUserName("张三");
        person.setPassWord("123456");
        person.setCreateTime(new Date());

        Query query = new Query(Criteria.where("id").is(person.getId()));
        Person result = mongoTemplate.findAndRemove(query, Person.class);
        System.out.println("删除的文档数据：" + result.toString());
    }

    /**
     * 删除符合条件的所有文档，并返回删除的文档
     * @throws Exception
     */
    @Test
    public void findAllAndRemove() throws Exception {
        Person person =new Person();
        person.setId(1l);
        person.setUserName("张三");
        person.setPassWord("123456");
        person.setCreateTime(new Date());

        Query query = new Query(Criteria.where("id").is(person.getId()));
        List<Person> result = mongoTemplate.findAllAndRemove(query, Person.class);
        System.out.println("删除的文档数据：" + result.toString());
    }

    /**
     * 查询集合中的全部文档数据
     * @throws Exception
     */
    @Test
    public void findAll() throws Exception {
        List<Person> result = mongoTemplate.findAll(Person.class);
        System.out.println("查询结果：" + result.toString());
    }

    /**
     * 查询集合中指定的ID文档数据
     * @throws Exception
     */
    @Test
    public void findById() {
        long id = 1l;
        Person result = mongoTemplate.findById(id, Person.class);
        System.out.println("查询结果：" + result.toString());
    }

    /**
     * 根据条件查询集合中符合条件的文档，返回第一条数据
     */
    @Test
    public void findOne() {
        String userName = "张三";
        Query query = new Query(Criteria.where("userName").is(userName));
        Person result = mongoTemplate.findOne(query, Person.class);
        System.out.println("查询结果：" + result.toString());
    }

    /**
     * 根据条件查询集合中符合条件的文档
     */
    @Test
    public void findByCondition() {
        String userName = "张三";
        Query query = new Query(Criteria.where("userName").is(userName));
        List<Person> result = mongoTemplate.find(query, Person.class);
        System.out.println("查询结果：" + result.toString());
    }


    /**
     * 根据条件查询集合中符合条件的文档，获取其文档列表并排序
     */
    @Test
    public void findByConditionAndSort() {
        String userName = "张三";
        Query query = new Query(Criteria.where("userName").is(userName)).with(Sort.by("age"));
        List<Person> result = mongoTemplate.find(query, Person.class);
        System.out.println("查询结果：" + result.toString());
    }

    /**
     * 根据单个条件查询集合中的文档数据，并按指定字段进行排序与限制指定数目
     */
    @Test
    public void findByConditionAndSortLimit() {
        String userName = "张三";
        //从第一行开始，查询2条数据返回
        Query query = new Query(Criteria.where("userName").is(userName)).with(Sort.by("createTime")).limit(2).skip(1);
        List<Person> result = mongoTemplate.find(query, Person.class);
        System.out.println("查询结果：" + result.toString());
    }

    /**
     * 根据【AND】关联多个查询条件，查询集合中的文档数据
     */
    @Test
    public void findByAndCondition() {
        // 创建条件
        Criteria criteriaUserName = Criteria.where("userName").is("张三");
        Criteria criteriaPassWord = Criteria.where("passWord").is("123456");
        // 创建条件对象，将上面条件进行 AND 关联
        Criteria criteria = new Criteria().andOperator(criteriaUserName, criteriaPassWord);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        List<Person> result = mongoTemplate.find(query, Person.class);
        System.out.println("查询结果：" + result.toString());
    }

    /**
     * 根据【OR】关联多个查询条件，查询集合中的文档数据
     */
    @Test
    public void findByOrCondition() {
        // 创建条件
        Criteria criteriaUserName = Criteria.where("userName").is("张三");
        Criteria criteriaPassWord = Criteria.where("passWord").is("123456");
        // 创建条件对象，将上面条件进行 AND 关联
        Criteria criteria = new Criteria().orOperator(criteriaUserName, criteriaPassWord);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        List<Person> result = mongoTemplate.find(query, Person.class);
        System.out.println("查询结果：" + result.toString());
    }

    /**
     * 根据【IN】关联多个查询条件，查询集合中的文档数据
     */
    @Test
    public void findByInCondition() {
        // 设置查询条件参数
        List<Long> ids = Arrays.asList(1l, 10l, 11l);
        // 创建条件
        Criteria criteria = Criteria.where("id").in(ids);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        List<Person> result = mongoTemplate.find(query, Person.class);
        System.out.println("查询结果：" + result.toString());
    }

    /**
     * 根据【逻辑运算符】查询集合中的文档数据
     */
    @Test
    public void findByOperator() {
        // 设置查询条件参数
        int min = 20;
        int max = 35;
        Criteria criteria = Criteria.where("age").gt(min).lte(max);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        List<Person> result = mongoTemplate.find(query, Person.class);
        System.out.println("查询结果：" + result.toString());
    }

    /**
     * 根据【正则表达式】查询集合中的文档数据
     */
    @Test
    public void findByRegex() {
        // 设置查询条件参数
        String regex = "^张*";
        Criteria criteria = Criteria.where("userName").regex(regex);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        List<Person> result = mongoTemplate.find(query, Person.class);
        System.out.println("查询结果：" + result.toString());
    }


    /**
     * 统计集合中符合【查询条件】的文档【数量】
     */
    @Test
    public void countNumber() {
        // 设置查询条件参数
        String regex = "^张*";
        Criteria criteria = Criteria.where("userName").regex(regex);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        long count = mongoTemplate.count(query, Person.class);
        System.out.println("统计结果：" + count);
    }


    /**
     * 创建升序索引
     */
    @Test
    public void createAscendingIndex() {
        // 设置字段名称
        String field = "userName";
        // 创建索引
        mongoTemplate.getCollection("persons").createIndex(Indexes.ascending(field));
    }


    /**
     * 创建升序唯一索引
     */
    @Test
    public void createUniqueIndex() {
        // 设置字段名称
        String field = "userName_1";
        // 配置索引选项
        IndexOptions options = new IndexOptions();
        // 设置为唯一索引
        options.unique(true);
        // 创建索引
        mongoTemplate.getCollection("persons").createIndex(Indexes.ascending(field), options);
    }


    /**
     * 根据索引名称移除索引
     */
    @Test
    public void removeIndex() {
        // 设置字段名称
        String field = "userName";
        // 删除索引
        mongoTemplate.getCollection("persons").dropIndex(field);
    }

    /**
     * 查询集合中所有的索引
     */
    @Test
    public void getIndexAll() {
        // 获取集合中所有列表
        ListIndexesIterable<Document> indexList = mongoTemplate.getCollection("persons").listIndexes();
        // 获取集合中全部索引信息
        for (Document document : indexList) {
            System.out.println("索引列表：" + document);
        }
    }









}

