package com.wxmblog.base.auth.listeners;

import com.wxmblog.base.auth.common.constant.AuthConstants;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author wang
 * @date 2023/11/30 17:55
 */
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        //调用一下就好
        if (AuthConstants.OPERATING) {

        }
    }
}
