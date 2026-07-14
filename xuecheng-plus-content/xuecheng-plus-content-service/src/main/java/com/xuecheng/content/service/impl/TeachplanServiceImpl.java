package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.content.mapper.*;
import com.xuecheng.content.model.dto.*;
import com.xuecheng.content.model.po.*;
import com.xuecheng.content.service.TeachplanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2023/2/12 10:16
 */
@Slf4j
@Service
public class TeachplanServiceImpl implements TeachplanService {
    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    TeachplanMediaMapper TeachplanMediaMapper;

/**
 * 根据课程ID查询课程章节数据并构建树形结构
 * @param courseId 课程ID
 * @return 返回课程章节数据的树形结构列表
 */
    @Override
    public List<TeachplanDto> selectTreeNodes(Long courseId) {
        // 获取课程章节数据
        LambdaQueryWrapper<Teachplan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Teachplan::getCourseId, courseId)
                          .orderByAsc(Teachplan::getParentid, Teachplan::getOrderby);
        List<Teachplan> teachplanList = teachplanMapper.selectList(lambdaQueryWrapper);
        if (CollectionUtils.isEmpty(teachplanList)) {
            return Collections.emptyList();
        }
        List<Long> ids = teachplanList.stream()
                                      .filter(item -> item.getParentid() != 0L)
                                      .map(Teachplan::getId)
                                      .collect(Collectors.toList());
        // 获取课程媒资
        List<TeachplanMedia> teachplanMedia = new ArrayList<>();
        if (!CollectionUtils.isEmpty(ids)) {
            LambdaQueryWrapper<TeachplanMedia> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
            lambdaQueryWrapper1.in(TeachplanMedia::getTeachplanId, ids);
            teachplanMedia = TeachplanMediaMapper.selectList(lambdaQueryWrapper1);
        }
        // 转成dto
        List<TeachplanDto> teachplanDtoList = teachplanList.stream()
                                                           .map(item -> {
                                                               TeachplanDto teachplanDto = new TeachplanDto();
                                                               BeanUtils.copyProperties(item, teachplanDto);
                                                               teachplanDto.setTeachPlanTreeNodes(new ArrayList<>());
                                                               return teachplanDto;
                                                           })
                                                           .collect(Collectors.toList());
        // 组装成树形数据，把每个节点按 parentid 关联到父节点的 teachPlanTreeNodes
        return buildTree(teachplanDtoList, teachplanMedia);
    }
    private int getCount(SaveTeachplanDto saveTeachplanDto) {
        Long courseId = saveTeachplanDto.getCourseId();
        Long parentid = saveTeachplanDto.getParentid();
        LambdaQueryWrapper<Teachplan> teachplanLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teachplanLambdaQueryWrapper.eq(Teachplan::getCourseId, courseId)
                                   .eq(Teachplan::getParentid, parentid);
        Integer count = teachplanMapper.selectCount(teachplanLambdaQueryWrapper);
        return count + 1;
    }
    @Override
    public void saveTeachplan(SaveTeachplanDto saveTeachplanDto) {
        Teachplan teachplan = new Teachplan();
        BeanUtils.copyProperties(saveTeachplanDto, teachplan);
        // 通过id判断是更新还是新增
        if (saveTeachplanDto.getId() == null) {
            teachplan.setStartTime(LocalDateTime.now());
            teachplanMapper.insert(teachplan);

            // 如果新增的是大章节

        } else {
            int count = getCount(saveTeachplanDto);
            teachplan.setOrderby(count);
            teachplanMapper.updateById(teachplan);
        }
    }

    private List<TeachplanDto> buildTree(List<TeachplanDto> dtos, List<TeachplanMedia> teachplanMedia) {
        // 建立 id -> node 的索引
        Map<Long, TeachplanDto> idMap = dtos.stream()
                                            .filter(item -> item.getId() != null)
                                            .collect(Collectors.toMap(TeachplanDto::getId, Function.identity(), (a, b) -> a));
        Map<Long, TeachplanMedia> mediaMap = teachplanMedia.stream()
                                                           .filter(item -> item.getTeachplanId() != null)
                                                           .collect(Collectors.toMap(TeachplanMedia::getTeachplanId, Function.identity(), (a, b) -> a));

        List<TeachplanDto> dtoList = new ArrayList<>();
        for (TeachplanDto dto : dtos) {
            Long parentid = dto.getParentid();
            // 如果是一级章节
            if (parentid == null || parentid == 0L) {
                dtoList.add(dto);
            } else {
                // 如果是二级，设置TeachPlanTreeNodes内容和媒资
                TeachplanDto parent = idMap.get(dto.getParentid());
                if (parent != null) {
                    // 设置媒资
                    Optional.ofNullable(mediaMap.get(dto.getId()))
                            .ifPresent(dto::setTeachplanMedia);

                    parent.getTeachPlanTreeNodes()
                          .add(dto);
                }

            }
        }
        sortTree(dtoList);
        return dtoList;
    }

    private void sortTree(List<TeachplanDto> nodes) {
        if (nodes == null || nodes.isEmpty()) return;
        nodes.sort(Comparator.comparing(n -> Optional.ofNullable(n.getOrderby())

                                                     .orElse(Integer.MAX_VALUE)));
        for (com.xuecheng.content.model.dto.TeachplanDto n : nodes) {
            sortTree(n.getTeachPlanTreeNodes());
        }
    }
}


