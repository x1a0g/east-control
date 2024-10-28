package com.east.control.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.east.control.model.FlowInfo;
import com.east.control.model.FlowItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FlowItemMapper extends BaseMapper<FlowItem> {

    @Insert("INSERT INTO flow_item VALUES (#{flow_id}, #{action_type}, #{x}, #{y}, #{cont}, #{create_time}, #{id})")
    void insertFlow(FlowItem flow);


    @Select("SELECT * FROM `flow_item` where flow_id=#{id} ORDER BY `create_time` asc")
    List<FlowItem> getOneById(String id);


    @Delete("delete from flow_item where id=#{id}")
    void delById(String id);

}