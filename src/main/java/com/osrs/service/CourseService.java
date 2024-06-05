package com.osrs.service;

import com.osrs.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author jacky
* @description 针对表【course】的数据库操作Service
* @createDate 2024-06-04 11:49:59
*/
public interface CourseService{
    List<Course> getCourseList();

    Map<String,Integer> countByDifficultyLevel();
}
