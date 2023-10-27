package com.wxmblog.base.websocket.netty;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuhm
 * @Description: 用户id和channel的关联关系处理
 */
public class ChannelMap {

    private static HashMap<Integer, Channel> manager = new HashMap<>();

    private static Map<String, Channel> online = new HashMap<>();

    public static void put(Integer key, Channel channel) {
        manager.put(key, channel);
    }


    public static Channel get(Integer userId) {
        return manager.get(userId);
    }

    public static HashMap<Integer, Channel> getManager() {
        return manager;
    }

    public static void putOnline(String key, Channel channel) {
        online.put(key, channel);
    }

    public static Channel getOnline(String ip) {
        return online.get(ip);
    }

    public static Map<String, Channel> getOnline() {
        return online;
    }
}
