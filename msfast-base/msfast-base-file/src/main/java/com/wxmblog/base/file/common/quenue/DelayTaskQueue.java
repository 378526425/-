package com.wxmblog.base.file.common.quenue;

import com.wxmblog.base.file.rest.types.DelayTask;
import lombok.Data;

import java.util.concurrent.DelayQueue;

/**
 * 延时队列
 * 需要保证队列单例
 */
@Data
public class DelayTaskQueue {

    private static class Holder {
        static DelayQueue<DelayTask> instance = new DelayQueue(); //单例保证队列唯一
    }

    public static DelayQueue<DelayTask> getInstance() {
        return Holder.instance;
    }
}
