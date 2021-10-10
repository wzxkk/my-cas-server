package org.wzx.mycasserver.core;

import cn.hutool.json.JSON;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @description: 调度
 * @author: 鱼头
 * @time: 2021-10-10 11:20
 */
@Slf4j
@Service
@DisallowConcurrentExecution
public class MyScheduler implements Job {
    @Autowired
    private Scheduler scheduler;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }

    public boolean startAutoTask() {
        return false;
    }

    public boolean start() {
        try {
            String taskId = "";
            // 创建触发器
            TriggerKey triggerKey = new TriggerKey(taskId);
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger != null) {
                scheduler.pauseTrigger(triggerKey);
                scheduler.unscheduleJob(triggerKey);
            }
            trigger = newTrigger().withIdentity(taskId).startNow().withSchedule(cronSchedule("taskInfo.getActionTime()"))
                    .build();
            JobKey jobKey = new JobKey(taskId);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                scheduler.pauseJob(jobKey);
                scheduler.deleteJob(jobKey);
            }
            // 创建作业
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("taskInfo", null);
            jobDetail = newJob(getClass()).setJobData(jobDataMap).withIdentity(taskId).build();
            // 启动调度
            Date date = scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * @方法说明:获取任务状态
     * @创建时间:2018年6月20日 下午3:46:53
     * @创建者:韦忠幸
     * @修改时间:
     * @修改人员:
     * @修改说明:
     */
    public String getTaskStatus(String taskId) {
        try {
            // 获取调度状态控制按钮
            TriggerKey triggerKey = new TriggerKey(taskId);
            if (triggerKey != null && scheduler != null && !scheduler.isShutdown()) {
                Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
                return triggerState.name();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public String getTaskNextFire(String taskId) {
        try {
            TriggerKey triggerKey = new TriggerKey(taskId);
            if (triggerKey != null && scheduler != null && !scheduler.isShutdown()) {
                Trigger trigger = scheduler.getTrigger(triggerKey);
                if (trigger != null) {
                    Date d = trigger.getNextFireTime();
                    if (d == null) {
                        return "";
                    }
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }
}
