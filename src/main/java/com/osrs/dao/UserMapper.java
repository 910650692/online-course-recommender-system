package com.osrs.dao;

import com.osrs.entity.Course;
import com.osrs.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author jacky
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-06-04 18:23:09
* @Entity com.osrs.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
        User selectByUsername(String username);

        int insertUser(User user);
        int countTotalUser();
        int addCourseToFavorite(Integer userId, Integer courseId);

        int removeCourseFromFavorite(Integer userId, Integer courseId);

        List<Course> selectFavoriteCoursesList(Integer userId);
}




