package com.osrs.dao;

import com.osrs.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* @author jacky
* @description 针对表【course】的数据库操作Mapper
* @createDate 2024-06-04 11:49:59
* @Entity com.osrs.entity.Course
*/

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    List<Course> selectList();
    @MapKey("DifficultyLevel")
    List<Map<String,Object>> countByDifficultyLevel();

    int updateCourse(Course course);

    int deleteCourse(Integer id);

    int insertCourse(Course course);

    int countTotalCourse();

    Double getAverageRating(Integer courseId);

    int updateCourseRating(Integer courseId, Double rating);
    Double getUserRating(Integer userId, Integer courseId);
    int insertUserRating(Integer userId,Integer courseId, Double rating);

    int updateUserRating(Integer userId,Integer courseId, Double rating);


}




