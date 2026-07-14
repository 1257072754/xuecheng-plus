package com.xuecheng.content;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.service.CourseCategoryService;
import com.xuecheng.content.service.TeachplanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2023/2/12 9:24
 */
@SpringBootTest
@Slf4j
public class CourseCategoryMapperTests {

    @Autowired
    CourseCategoryMapper courseCategoryMapper;
    @Autowired
    CourseCategoryService courseCategoryService;
    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    TeachplanService teachplanService;

    @Test
    public void test(){
        List<CourseCategoryTreeDto> categoryTreeDtoList = courseCategoryService.queryTreeNodes("1");
        log.info("categoryTreeDtoList:{}", categoryTreeDtoList);
    }
/**
 * 测试方法：用于测试课程分类树形结构查询功能
 * 通过调用courseCategoryService的queryTreeNodes方法，查询ID为"1"的课程分类树形结构数据
 * 并将查询结果输出到日志中
 */
    @Test
    public void test1(){
    // 调用课程分类服务，查询根节点ID为"1"的课程分类树形结构数据
        List<TeachplanDto> teachplanList = teachplanMapper.selectTreeNodes(117L);
        // 使用日志输出查询结果，方便调试和验证
        log.info("teachplanList:{}", teachplanList);
    }
    @Test
    public void test2(){
    // 调用课程分类服务，查询根节点ID为"1"的课程分类树形结构数据
        List<TeachplanDto> teachplanList = teachplanService.selectTreeNodes(117L);
        // 使用日志输出查询结果，方便调试和验证
        log.info("teachplanList:{}", teachplanList);
    }
}
