package com.xuecheng.content.model.dto;

import com.xuecheng.base.exception.ValidationGroups;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
@Data
public class EditCourseDto extends  AddCourseDto{
    @ApiModelProperty(value = "课程id", required = true)
    private  Long id;
}
