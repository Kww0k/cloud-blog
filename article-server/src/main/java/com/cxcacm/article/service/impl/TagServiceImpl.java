package com.cxcacm.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxcacm.article.entity.ResponseResult;
import com.cxcacm.article.entity.Tag;
import com.cxcacm.article.mapper.TagMapper;
import com.cxcacm.article.service.TagService;
import com.cxcacm.article.service.vo.TagListVo;
import com.cxcacm.article.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-05-03 16:06:56
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult getTagList() {
        List<Tag> tagList = baseMapper.selectList(null);
        List<Tag> list = tagList
                .stream()
                .filter(tag -> tag.getParentId() == null)
                .toList();
        List<TagListVo> tagListVoList = BeanCopyUtils.copyBeanList(list, TagListVo.class);
        for (TagListVo tagListVo : tagListVoList) {
            List<TagListVo> childrenList = new ArrayList<>();
            for (Tag tag : tagList)
                if (Objects.equals(tag.getParentId(), tagListVo.getId()))
                    childrenList.add(new TagListVo(tag.getId(), tag.getName(), null));
            tagListVo.setTagListVos(childrenList);
        }
        return ResponseResult.okResult(tagListVoList);
    }
}
