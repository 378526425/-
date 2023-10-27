package com.wxmblog.base.websocket.common.rest.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-02-06 14:46
 **/

@Data
public class MessageListResponse implements Serializable {

    private Integer userId;

    private Integer sendUserId;

    private String latelyTime;

    private Integer unreadCount;

    private String messageDescribe;

    private String messageDescribeFormat;

    private String nickName;

    private String headPortrait;

}
