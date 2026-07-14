package com.xuecheng.content.api;

import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教学计划控制器类 * 该类用于处理教学计划相关的请求和业务逻辑
 */
@Slf4j
@Api(tags = "教学计划管理接口")
@RestController
public class TeachplanController {
    @Autowired
    private TeachplanMapper teachplanMapper;
    @Autowired
    private TeachplanService teachplanService;

    /**
     * 查询课程计划树形结构 * @param courseId 课程id * @return 课程计划树形结构
     */
    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId", name = "课程Id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreesByCourseId(@PathVariable Long courseId) {
        return teachplanService.selectTreeNodes(courseId);
    }

    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan(@Validated @RequestBody SaveTeachplanDto teachplan) {
        log.info("teachplan:{}", teachplan);
         teachplanService.saveTeachplan(teachplan);
    }
}