package com.wxmblog.base.websocket.netty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @ClassName：NettyBooter
 * @Description: 初始化程序后加载此方法
 * @Author: wangxiaomu
 * @Date: 2020/3/9 0009 上午 10:49
 */
@Component
@Slf4j
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    WSServer wsServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("开始启动websocket");
        wsServer.start();
    }

}
