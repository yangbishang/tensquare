package com.tensquare.base.dao;

import com.tensquare.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LabelDao extends JpaRepository<Label, String > ,
                                     JpaSpecificationExecutor<Label> {  //条件查询，排序，分页查询等高级功能需要
}
