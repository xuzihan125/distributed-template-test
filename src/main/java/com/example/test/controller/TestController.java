package com.example.test.controller;

import com.example.test.entity.TestObject;
import com.example.test.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestMapper testMapper;

    @GetMapping("/hello")
    public String getResponse(){
        return "this is response";
    }

    @GetMapping("/get")
    public String getAllEntity(){
        return testMapper.getAllEntity().toString();
    }

    @GetMapping("/add")
    public String add(){
        testMapper.addContent("this is a test content");
        return getAllEntity();
    }

    @GetMapping("/update")
    public String update(){
        long id = testMapper.getAllEntity().get(0).getId();
        testMapper.updateContent("this is updated", id);
        return getAllEntity();
    }

    @GetMapping("/del")
    public String del(){
        long id = testMapper.getAllEntity().get(0).getId();
        testMapper.deleteContent(id);
        return getAllEntity();
    }
}
