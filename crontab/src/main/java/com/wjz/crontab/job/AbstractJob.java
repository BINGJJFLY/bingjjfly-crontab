package com.wjz.crontab.job;

import java.lang.reflect.Type;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.wjz.crontab.utils.ReflectUtils;

public abstract class AbstractJob<T> extends QuartzJobBean {

	@Value("${context.key}")
	private String contextKey;
	private Type serviceClass;
	private String serviceName;

	protected AbstractJob() {
	}

	protected AbstractJob(String serviceName) {
		this.serviceClass = ReflectUtils.getSingleTypeParameter(getClass());
		this.serviceName = serviceName;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		T service = getService(context);
		doExecute(service);
	}

	protected abstract void doExecute(T service);

	@SuppressWarnings("unchecked")
	private T getService(JobExecutionContext context) {
		try {
			ApplicationContext applicationContext = (ApplicationContext) context.getScheduler().getContext()
					.get(contextKey);
			return (T) applicationContext.getBean(serviceName, serviceClass);
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
