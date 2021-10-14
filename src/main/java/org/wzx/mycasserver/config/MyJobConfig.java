package org.wzx.mycasserver.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Properties;
/**
 * @description:
 * @author: 鱼头(韦忠幸)
 * @since: 2021-10-13 14:41
 * @version: 0.0.1
 */
@Component
@Configuration
public class MyJobConfig extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        schedulerFactoryBean.setJobFactory(this); //指向自建的调度工厂，用于解决方法类无法注入的问题
        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        scheduler.start();// 服务启动shi
        return scheduler;
    }

    public Properties quartzProperties() {
        Properties prop = new Properties();
        prop.put("org.quartz.jobStore.misfireThreshold", "1000");// 错过多少秒可再恢复
        prop.put("org.quartz.threadPool.threadCount", "5");// 指定线程数量
        return prop;
    }

}
