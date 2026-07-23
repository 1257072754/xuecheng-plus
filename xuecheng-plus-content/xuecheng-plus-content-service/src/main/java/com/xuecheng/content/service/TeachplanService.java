package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.*;
import com.xuecheng.content.model.po.CourseBase;

import java.util.List;

/**
 * @author Mr.M
 * @version 1.0
 * @description 课程信息管理接口
 * @date 2023/2/12 10:14
 */
public interface TeachplanService {

    List<TeachplanDto> selectTreeNodes(Long courseId);
    void saveTeachplan(SaveTeachplanDto teachplan);
    void delTeachplan(Long id);
    void movedownTeachplan(Long id);
    void moveupTeachplan(Long id);
}
