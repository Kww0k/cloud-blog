package com.cxcacm.community.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Silvery
 * @Date: 2023/6/12 21:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityPage {
    Long count;
    List<CommunityListVo> communityListVos;
}
