package com.xuecheng.content.service;

import com.xuecheng.content.model.po.CourseTeacher;

import java.util.List;
public interface CourseTeacherService {
    List<CourseTeacher> queryTeacherList(Long id);
    CourseTeacher addTeacher(CourseTeacher courseTeacher);
}
