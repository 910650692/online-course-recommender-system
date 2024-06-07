package com.osrs.controller;

import com.osrs.common.CommonRes;
import com.osrs.common.JsonResponse;
import com.osrs.entity.Course;
import com.osrs.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @RequestMapping("/countByDifficultyLevel")
    public JsonResponse countByDifficultyLevel(){
        Map<String, Integer> data = courseService.countByDifficultyLevel();

        if (data == null)
            return new JsonResponse(400, "failed", null);
        else
            return new JsonResponse(200, "success", courseService.countByDifficultyLevel());
    }

    @RequestMapping("/update")
    public JsonResponse update(@RequestBody Course course){
        int result = courseService.updateCourse(course);
        if(result == 1){
            return new JsonResponse(200, "success", null);
        }else{
            return new JsonResponse(400, "failed", null);
        }
    }
    @RequestMapping("/delete")
    public JsonResponse delete(@RequestParam Integer id){
        System.out.println("Deleting course with id: " + id);
        int result = courseService.deleteCourse(id);
        System.out.println("Delete result: " + result);
        if(result == 1){
            return new JsonResponse(200, "success", null);
        }else{
            return new JsonResponse(400, "failed", null);
        }
    }
    @RequestMapping("/insert")
    public JsonResponse insert(@RequestBody Course course){
        int result = courseService.insertCourse(course);
        if(result == 1){
            return new JsonResponse(200, "success", null);
        }else{
            return new JsonResponse(400, "failed", null);
        }
    }
    @RequestMapping("/total")
    public JsonResponse total(){
        int totalCourse = courseService.countTotalCourse();

        return new JsonResponse(200, "success", totalCourse);
    }

    @RequestMapping("/rating")
    public JsonResponse rating(@RequestParam Integer userId, @RequestParam Integer courseId, @RequestParam Double rating){
        int result = courseService.RateCourse(userId, courseId, rating);
        if(result == 1){
            return new JsonResponse(200, "Rating success", result);
        }else{
            return new JsonResponse(400, "Rating failed", result);
        }
    }


}
