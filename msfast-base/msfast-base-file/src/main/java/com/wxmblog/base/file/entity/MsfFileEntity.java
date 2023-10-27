package com.wxmblog.base.file.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wxmblog.base.file.common.enums.FileStatusEnum;
import lombok.Data;

import java.util.Date;


/**
 * 备注
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-11-12 23:52:07
 */
@Data
@TableName(value = "msf_file", autoResultMap = true)
public class MsfFileEntity {

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
     * 乐观锁版本号
     */
    @Version
    private Integer version;

    /**
     * 是否是原图
     */
    @TableField("original")
    private Boolean original;
    /**
     * 文件路径
     */
    @TableField("url")
    private String url;
    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 文件状态 TEMP-临时文件 SAVED-已保存的文件
     */
    @TableField("status")
    private FileStatusEnum status;

}
