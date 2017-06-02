package com.acfun.dao;

import com.acfun.domain.Person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * Created by jianghong on 2017/5/22.
 */
@Mapper
public interface PersonMapper {
    @Select("select * from people where first_name=#{lastName} and last_name=#{firstName}")
    Person selectOneByEnity(Person person);
    @Select("select * from people where first_name=#{lastName,jdbcType=VARCHAR} and last_name=#{firstName,jdbcType=VARCHAR}")
    Person selectOneByMap(Map<String,String> map);
}
