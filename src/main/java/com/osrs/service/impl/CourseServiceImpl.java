package com.osrs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.osrs.entity.Course;
import com.osrs.service.CourseService;
import com.osrs.dao.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author jacky
* @description 针对表【course】的数据库操作Service实现
* @createDate 2024-06-04 11:49:59
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService{
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public List<Course> getCourseList() {
        return courseMapper.selectList();
    }
}




