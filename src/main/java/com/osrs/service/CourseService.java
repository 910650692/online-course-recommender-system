package com.osrs.service;

import com.osrs.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author jacky
* @description 针对表【course】的数据库操作Service
* @createDate 2024-06-04 11:49:59
*/
public interface CourseService extends IService<Course> {
    List<Course> getCourseList();
}
