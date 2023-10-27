package com.wxmblog.base.websocket.thread;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.wxmblog.base.common.constant.ConfigConstants;
import com.wxmblog.base.common.constant.Constants;
import com.wxmblog.base.common.service.RedisService;
import com.wxmblog.base.websocket.common.constant.WebSocketConstants;
import com.wxmblog.base.websocket.common.rest.request.BaseMessageInfo;
import com.wxmblog.base.websocket.common.rest.response.BaseMessageInfoResponse;
import com.wxmblog.base.websocket.common.rest.response.MessageInfoResponse;
import com.wxmblog.base.websocket.service.MsFastMessageService;
import com.wxmblog.base.websocket.utils.ChannelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.concurrent.TimeUnit;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-12 17:23
 **/
@Slf4j
public class SendRunnable implements Runnable {


    private ChannelUtil channelUtil;

    private BaseMessageInfo messageInfo;

    private RedisService redisService;

    MsFastMessageService msFastMessageService;

    public SendRunnable(ChannelUtil channelUtil, BaseMessageInfo messageInfo, RedisService redisService, MsFastMessageService msFastMessageService) {
        this.channelUtil = channelUtil;
        this.messageInfo = messageInfo;
        this.redisService = redisService;
        this.msFastMessageService = msFastMessageService;
    }

    @Override
    public void run() {
        log.info("开始发送消息");
        BaseMessageInfoResponse baseMessageInfoResponse = new BaseMessageInfoResponse();
        BeanUtils.copyProperties(messageInfo, baseMessageInfoResponse);
        baseMessageInfoResponse.setMsgNo(UUID.fastUUID().toString().replaceAll("-",""));

        //消息持久化
        MessageInfoResponse messageInfoResponse = new MessageInfoResponse();
        BeanUtils.copyProperties(messageInfo, messageInfoResponse);
        messageInfoResponse.setMsgNo(baseMessageInfoResponse.getMsgNo());
        redisService.redisTemplate.opsForZSet().add(channelUtil.getMessageInfoKey(messageInfo.getSendUserId(), messageInfo.getAcceptUserId()), messageInfoResponse, System.currentTimeMillis());

        //修改未读数
        Integer unRead = redisService.getCacheObject(messageInfo.getAcceptUserId() + WebSocketConstants.MSG_UN_READ + messageInfo.getSendUserId());
        if (unRead == null) {
            redisService.setCacheObject(messageInfo.getAcceptUserId() + WebSocketConstants.MSG_UN_READ + messageInfo.getSendUserId(), 1);
        } else {
            redisService.setCacheObject(messageInfo.getAcceptUserId() + WebSocketConstants.MSG_UN_READ + messageInfo.getSendUserId(), unRead + 1);
        }

        //更新列表
        msFastMessageService.addMessageList(messageInfo, messageInfo.getSendUserId(), messageInfo.getAcceptUserId());
        msFastMessageService.addMessageList(messageInfo, messageInfo.getAcceptUserId(), messageInfo.getSendUserId());

        //发送消息
        channelUtil.sendText(messageInfo.getAcceptUserId(), JSON.toJSONString(baseMessageInfoResponse));

        //todo 需要删除
        redisService.setCacheObject(baseMessageInfoResponse.getMsgNo(), Constants.MSG_ANSWER, ConfigConstants.RESEND_TIME(), TimeUnit.SECONDS);
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String value = redisService.getCacheObject(baseMessageInfoResponse.getMsgNo());
            if (StringUtils.isNotBlank(value)) {
                channelUtil.sendText(messageInfo.getAcceptUserId(), JSON.toJSONString(baseMessageInfoResponse));
            } else {
                break;
            }
        }


    }
}
