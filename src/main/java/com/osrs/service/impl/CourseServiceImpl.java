package com.osrs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.osrs.dao.CourseMapper;
import com.osrs.entity.Course;
import com.osrs.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author jacky
* @description 针对表【course】的数据库操作Service实现
* @createDate 2024-06-04 11:49:59
*/
@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public List<Course> getCourseList() {
        return courseMapper.selectList();
    }

    @Override
    public Map<String,Integer> countByDifficultyLevel() {
        List<Map<String,Object>> results = courseMapper.countByDifficultyLevel();
//        System.out.println(results);
        Map<String,Integer> result = new HashMap<>();
        for(Map<String,Object> map : results){
            String difficultyLevel = map.get("DifficultyLevel").toString();
            Integer count = Integer.parseInt(map.get("count").toString());
            result.put(difficultyLevel,count);
        }
//        System.out.println(result);
        return result;
    }
    @Override
    public int updateCourse(Course course) {
        return courseMapper.updateCourse(course);
    }
    @Override
    public int deleteCourse(Integer id) {
        System.out.println("Service Deleting course with id: " + id);
        int result = courseMapper.deleteCourse(id);
        System.out.println("Service Delete result: " + result);
        return result;
    }
    @Override
    public int insertCourse(Course course) {
        return courseMapper.insertCourse(course);
    }

    @Override
    public int countTotalCourse() {
        return courseMapper.countTotalCourse();
    }
    @Override
    public Double getAverageRating(Integer courseId) {
        return courseMapper.getAverageRating(courseId);
    }

    @Override
    public int insertUserRating(Integer userId, Integer courseId, Double rating) {
        return courseMapper.insertUserRating(userId, courseId, rating);
    }

    @Override
    public int updateUserRating(Integer userId, Integer courseId, Double rating) {
        return courseMapper.updateUserRating(userId, courseId, rating);
    }

    @Override
    public Double getUserRating(Integer userId, Integer courseId) {
        return courseMapper.getUserRating(userId, courseId);
    }

    @Override
    public int RateCourse(Integer userId, Integer courseId, Double rating) {
        Double existingRating = courseMapper.getUserRating(userId, courseId);
        int result = 0;
        if(existingRating == null){
            result = courseMapper.insertUserRating(userId, courseId, rating);
        }else{
           result = courseMapper.updateUserRating(userId, courseId, rating);
        }
        Double newAverageRating = courseMapper.getAverageRating(courseId);
        courseMapper.updateCourseRating(courseId, newAverageRating);
        return result;
    }



}




