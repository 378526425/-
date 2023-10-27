package com.wxmblog.base.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxmblog.base.auth.entity.MsfConfigEntity;

/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-10-10 15:45:08
 */
public interface MsfConfigService extends IService<MsfConfigEntity> {

    /**
     * @Description: 根据code查询value
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/10/10 下午4:08
     */
    String getValueByCode(String code);

    MsfConfigEntity getConfigByCode(String code);
}

