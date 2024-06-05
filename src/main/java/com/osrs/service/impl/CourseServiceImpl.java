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
}




