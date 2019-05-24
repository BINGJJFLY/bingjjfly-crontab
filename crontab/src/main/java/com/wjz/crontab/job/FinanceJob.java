package com.wjz.crontab.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.stereotype.Component;

import com.wjz.crontab.service.FinanceService;

@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class FinanceJob extends AbstractJob<FinanceService> {

	@Override
	protected void doExecute(FinanceService service) {
		service.expired();
	}

}
