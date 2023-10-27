package com.wxmblog.base.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wxmblog.base.auth.common.enums.ConfigAccessEnum;
import com.wxmblog.base.common.entity.BaseEntity;
import lombok.Data;


/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-10-10 15:45:08
 */
@Data
@TableName("msf_config")
public class MsfConfigEntity extends BaseEntity {

    /**
     * 设置编号
     */
    @TableField("code")
    private String code;
    /**
     * 系统值
     */
    @TableField("value")
    private String value;
    /**
     * 描述信息
     */
    @TableField("descr")
    private String descr;


    @TableField("access")
    private ConfigAccessEnum access;
}
