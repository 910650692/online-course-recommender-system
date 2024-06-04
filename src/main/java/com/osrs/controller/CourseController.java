package com.osrs.controller;

import com.osrs.common.CommonRes;
import com.osrs.common.JsonResponse;
import com.osrs.entity.Course;
import com.osrs.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;


    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "test";
    }

    @RequestMapping("/list")
    public JsonResponse list(){
        List<Course> courseList = courseService.getCourseList();
        return new JsonResponse(200, "success", courseList);
    }

}
