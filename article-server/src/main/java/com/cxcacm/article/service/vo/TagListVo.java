package com.cxcacm.article.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListVo {
    private Long id;
    //标签名
    private String name;
    private List<TagListVo> tagListVos;
}
