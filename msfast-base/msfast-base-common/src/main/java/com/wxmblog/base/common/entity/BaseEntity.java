package com.wxmblog.base.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-29 16:37
 **/
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改者
     */
    private String modifyer;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableLogic
    private Integer delFlag;
    /**
     * 乐观锁版本号
     */
    @Version
    private Integer version;
}
