package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部数据
     * @return
     */
    @RequestMapping(method= RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    public Result findOne(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(id));
    }

    /**
     * 增加
     * @param spit
     * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    public Result add(@RequestBody Spit spit ){
        spitService.add(spit);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    /**
     * 修改
     * @param spit
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public Result update(@RequestBody Spit spit,@PathVariable String id )
    {
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id ){
        spitService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/comment/{parentid}/{page}/{size}" , method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid , @PathVariable int page , @PathVariable int size){
        Page<Spit> pageDate = spitService.findByParentid(parentid , page  , size);
        return new Result(true , StatusCode.OK , "查询成功" , new PageResult<Spit>(pageDate.getTotalElements(), pageDate.getContent()));
    }

    /**
     * 点赞
     * @param id
     * @return
     */
    @RequestMapping(value="/thumbup/{id}",method=RequestMethod.PUT)
    public Result updateThumbup(@PathVariable String id){
        //判断当前用户是否已经点赞，但是现在我们没有做认证，暂时把userid写死
        String userid = "111";
        //判断当前用户是否已经点赞
        if(redisTemplate.opsForValue().get("thumbup_"+userid)!= null){
            return new Result(false , StatusCode.REPERROR , "不能重复点赞");
        }
        spitService.updateThumbup(id);
        redisTemplate.opsForValue().set("thumbup_"+userid , 1);
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}
