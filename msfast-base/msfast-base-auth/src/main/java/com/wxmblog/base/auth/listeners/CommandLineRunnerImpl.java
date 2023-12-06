package com.wxmblog.base.auth.listeners;

import com.wxmblog.base.auth.common.constant.AuthConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author wang
 * @date 2023/11/30 17:55
 */
@Component
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        log.info("Authorization result:{}", AuthConstants.OPERATING);
    }
}
