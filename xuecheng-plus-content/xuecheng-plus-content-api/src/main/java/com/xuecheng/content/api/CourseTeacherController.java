package com.xuecheng.content.api;

import com.xuecheng.content.model.po.CourseTeacher;
import org.springframework.web.bind.annotation.*;
import com.xuecheng.content.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 课程-教师关系表 前端控制器
 * </p>
 *
 * @author itcast
 */
@Slf4j
@RestController
@RequestMapping("courseTeacher")
@Api(tags = "课程-教师关系接口")
public class CourseTeacherController {

    @Autowired
    private CourseTeacherService courseTeacherService;

    /**
     * 查询课程下的老师
     */
    @ApiOperation("查询教师")
    @GetMapping("/list/{id}")
    public List<CourseTeacher> queryTeacherList(@PathVariable Long id) {
        return courseTeacherService.queryTeacherList(id);
    }
    /**
     * 新增教师
     */
    @ApiOperation("查询教师")
    @PostMapping()
    public CourseTeacher addTeacher(@RequestBody CourseTeacher courseTeacher) {
        return courseTeacherService.addTeacher(courseTeacher);
    }

}
