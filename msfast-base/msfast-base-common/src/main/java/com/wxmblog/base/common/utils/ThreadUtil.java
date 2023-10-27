package com.wxmblog.base.common.utils;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-12 16:28
 **/

import lombok.experimental.Delegate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadUtil {

    //维护一个单例线程
    private static final ThreadUtil threadUtil = new ThreadUtil();

    /**
     * @Description: 用来创建一个可以无限扩大的线程池，适用于负载较轻的场景，执行短期异步任务。
     * （可以使得任务快速得到执行，因为任务时间执行短，可以很快结束，也不会造成cpu过度切换）
     */
    @Delegate
    public ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    /**
     * @Description: 创建一个固定大小的线程池，因为采用无界的阻塞队列，所以实际线程数量永远不会变化，
     * 适用于负载较重的场景，对当前线程数量进行限制。（保证线程数可控，不会造成线程过多，导致系统负载更为严重）
     */
    @Delegate
    public ExecutorService fixedThreadPool = Executors.newFixedThreadPool(200);

    /**
     * @Description: 创建一个定长线程池 支持定时及周期性任务执行。
     * <p>
     * 使用方法： ThreadUtil.getInstance().schedule(new TestThread(),3, TimeUnit.SECONDS);
     */
    @Delegate
    public ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(200);


    /**
     * @Description: 创建一个单线程的线程池，适用于需要保证顺序执行各个任务
     */
    @Delegate
    public ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    public static ThreadUtil getInstance() {
        return threadUtil;
    }

}
