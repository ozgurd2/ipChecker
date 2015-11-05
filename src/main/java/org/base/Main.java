package org.base;

import org.base.JobOne;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws ParseException, SchedulerException {

        JobDetail jobOne = new JobDetail();
        jobOne.setName("job_ip_sender");
        jobOne.setJobClass(JobOne.class);

        CronTrigger trigger = new CronTrigger();
        trigger.setName("trigger_ip_sender");
        trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
        trigger.setCronExpression("0/30 * * * * ?");
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();

        scheduler.scheduleJob(jobOne, trigger);
    }
}
