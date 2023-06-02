package com.cxcacm.community.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 收藏表(CommunityCollection)表实体类
 *
 * @author makejava
 * @since 2023-06-02 22:18:30
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("community_collection")
public class CommunityCollection  {
    //用户名@TableId
    private String username;
    @TableId
    private Long articleId;




}
