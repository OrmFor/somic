package com.yinmimoney.web.p2pnew.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
@EnableScheduling
public class TestConcurrentTask extends BaseTask {
    private static final Logger LOGGER = LogManager.getLogger(TestConcurrentTask.class);

    @Scheduled(cron="*/15 * * * * *")//秒 分 时 日 月 星期几
    public void run() {
        super.run();
    }

    @Override
    protected void doTask() {
        modifyThreadName();

        int i =0;
        while (true){
            LOGGER.info("当前线程："+Thread.currentThread().getName()+"------->测试打印次数: " + i++);

            try {
                Thread.sleep( 1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (20 == i) {
                break;
            }
        }

    }

    @Override
    protected String modifyThreadName() {
        Thread.currentThread().setName(this.getClass().getSimpleName());
        LOGGER.info(MessageFormat.format("当前线程：{0}",Thread.currentThread().getName()));
        return Thread.currentThread().getName();
    }
}
