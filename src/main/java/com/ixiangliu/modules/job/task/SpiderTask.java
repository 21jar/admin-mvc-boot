package com.ixiangliu.modules.job.task;

import com.ixiangliu.modules.spider.controller.Spider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * 测试定时任务(演示Demo，可删除)
 *
 * testTask为spring bean的名称
 */
@Slf4j
@Component("spiderTask")
public class SpiderTask implements ITask {
	@Override
	public void run(String params){
		Random random = new Random();
		int randNum = random.nextInt(10);
		log.info(randNum + "");
		try {
			Thread.sleep(randNum*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("执行定时任务时间: " + LocalDateTime.now());
		Spider crawler = new Spider("crawl", true);
		try {
			crawler.start(4);//启动爬虫
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
