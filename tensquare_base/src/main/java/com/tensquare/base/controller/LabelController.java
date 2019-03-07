package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.Result;
import entity.StatusCode;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;


    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询成功",labelService.findAll());
    }

    @RequestMapping(value = "{labelId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId){

        return new Result(true, StatusCode.OK, "查询成功",labelService.findById(labelId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label){    //传过来的json对象要转javaBean用注解RequestBody
        labelService.save(label);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value="/{labelId}" , method = RequestMethod.PUT)
    public Result update(@PathVariable String labelId , @RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "更新成功");

    }

    @RequestMapping(value="/{labelId}" ,method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String labelId){
        labelService.deleteById(labelId);
        return new Result(true , StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label){
        List<Label> list = labelService.findSearch(label);
        for(int i=0; i<list.size() ; i++){
            System.out.println(list.get(i));
        }
        return new Result(true , StatusCode.OK , "查询成功",list);
    }
}
