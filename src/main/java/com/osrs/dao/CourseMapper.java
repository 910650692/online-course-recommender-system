package com.osrs.dao;

import com.osrs.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author jacky
* @description 针对表【course】的数据库操作Mapper
* @createDate 2024-06-04 11:49:59
* @Entity com.osrs.entity.Course
*/

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    List<Course> selectList();
}




