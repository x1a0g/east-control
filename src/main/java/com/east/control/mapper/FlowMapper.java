package com.east.control.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.east.control.model.FlowInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FlowMapper extends BaseMapper<FlowInfo> {

    @Insert("insert into flow values (#{id},#{name},#{desc},#{create_time})")
    void insertFlow(FlowInfo flow);


    @Select("select * from flow")
    List<FlowInfo> getAll();

}