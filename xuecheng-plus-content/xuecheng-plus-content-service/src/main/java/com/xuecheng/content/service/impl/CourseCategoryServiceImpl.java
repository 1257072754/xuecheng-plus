package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2023/2/12 14:49
 */
@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        // 获取数据
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);
        // 封装数据，找到每个节点的子节点封装成List<CourseCategoryTreeDto>
        // 转成map
        Map<Object, CourseCategoryTreeDto> categoryTreeDtoMap = courseCategoryTreeDtos
                .stream()
                .filter(item -> !id.equals(item.getId()))
                .collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));
        List<CourseCategoryTreeDto> arrayList = new ArrayList<>();
        // 一边遍历一边找子节点放在父节点的childrenTreeNodes
        courseCategoryTreeDtos
                .stream()
                .filter(item -> !id.equals(item.getId()))
                .forEach(item -> {
                    if (item
                            .getParentid()
                            .equals(id)) {
                        arrayList.add(item);
                    }
                    // 获取父节点
                    CourseCategoryTreeDto courseCategoryParent = categoryTreeDtoMap.get(item.getParentid());
                    // 1. 判断父节点是否存在，防止数据不一致导致的NPE
                    if (courseCategoryParent != null) {
                        // 2. 判断父节点的子节点集合是否为空，如果为空则初始化
                        if (courseCategoryParent.getChildrenTreeNodes() == null) {
                            courseCategoryParent.setChildrenTreeNodes(new ArrayList<>());
                        }
                        // 3. 将当前节点添加到父节点的子节点集合中
                        courseCategoryParent.getChildrenTreeNodes().add(item);}
                });


        return arrayList;
    }
}
