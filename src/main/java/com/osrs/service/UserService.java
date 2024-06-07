package com.osrs.service;

import com.osrs.dao.UserMapper;
import com.osrs.entity.Course;
import com.osrs.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
* @author jacky
* @description 针对表【user】的数据库操作Service
* @createDate 2024-06-04 18:23:35
*/
public interface UserService extends IService<User> {
    User login(String username, String password);

    User getUserInfo(String username);

    int register(User user);

    int countTotalUser();

    int addCourseToFavorite(Integer userId, Integer courseId);
    int removeCourseFromFavorite(Integer userId, Integer courseId);

    List<Course> getFavoriteCoursesList(Integer userId);
}
