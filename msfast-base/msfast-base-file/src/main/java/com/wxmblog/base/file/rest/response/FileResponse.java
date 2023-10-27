package com.wxmblog.base.file.rest.response;

import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-07-27 15:02
 **/

@Data
public class FileResponse {

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件地址
     */
    private String url;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("name", getName())
                .append("url", getUrl())
                .toString();
    }
}
