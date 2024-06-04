package com.osrs.entity;


import java.io.Serializable;



/**
* 
* @TableName course
*/
public class Course implements Serializable {

    /**
     *
     */
    private Integer id;
    /**
     *
     */

    private String courseName;
    /**
     *
     */

    private String university;
    /**
     *
     */

    private String difficultyLevel;
    /**
     *
     */

    private String courseRating;
    /**
     *
     */

    private String courseUrl;
    /**
     *
     */

    private String courseDescription;
    /**
     *
     */

    private String skills;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getCourseRating() {
        return courseRating;
    }

    public void setCourseRating(String courseRating) {
        this.courseRating = courseRating;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}


