package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
@Slf4j
@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {
    @Autowired
    private CourseTeacherMapper courseTeacherMapper;
    @Override
    public List<CourseTeacher> queryTeacherList(Long id) {
        LambdaQueryWrapper<CourseTeacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CourseTeacher::getCourseId, id);
        List<CourseTeacher> courseTeachers = courseTeacherMapper.selectList(lambdaQueryWrapper);
        if (courseTeachers.isEmpty()) {
            return Collections.emptyList();
        }
        return courseTeachers;
    }
    @Override
    public CourseTeacher addTeacher(CourseTeacher courseTeacher) {
        courseTeacher.setCreateDate(LocalDateTime.now());
        int insert = courseTeacherMapper.insert(courseTeacher);
        if (insert < 1) XueChengPlusException.cast("新增失败");
        return courseTeacher;
    }
}
