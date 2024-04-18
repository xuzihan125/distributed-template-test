package com.example.test.mapper;

import com.example.test.entity.TestObject;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TestMapper {

    @Select("SELECT * FROM test_table")
    List<TestObject> getAllEntity();

    @Insert("insert into test_table(content) values(#{content})")
    int addContent(String content);

    @Update("update test_table set content=#{content} where id=#{id}")
    int updateContent(String content, Long id);

    @Delete("delete from test_table where id=#{id}")
    int deleteContent(Long id);


}
