package com.sogeti.filmLand.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sogeti.filmLand.dao.SubscribedServices;
import com.sogeti.filmLand.service.RestService;
import com.sogeti.filmLand.Constant.ConstantValue;

@Component
public class OverDuePaymentScheduler {

	private static final Logger FILMLAND_LOG = LoggerFactory.getLogger("FILMLAND_LOG");
	@Autowired
	private RestService restService;

	/**This method will run the scheduler in each 10 Sec interval to fetch the over due subscription user
	 * 
	 */
	@Scheduled(cron=ConstantValue.CRON_EXPRESSION)
	public void trackOverduePayments() {
		List<SubscribedServices> subscribedServices = restService.getSubscribedService();
		subscribedServices.forEach(subscribedService -> {
			if (isBeforeMonths(-1, subscribedService.getStartDate())) {
				FILMLAND_LOG.warn("OverDueNotification: Mail has been sent Subscriber {} for over due payment ",
						subscribedService.getUser_name());
			}
		});
	}

	/**This method will compare the start date with current date and return response in 0/1(boolean) 
	 * @param months
	 *       Contain the start date of subscription month 
	 * @param aDate
	 *       Contain the start date of subscription date
	 * @return
	 *       return true or false after date calculation
	 */
	private boolean isBeforeMonths(int months, Date aDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, months);
		return aDate.compareTo(calendar.getTime()) < 0;
	}
}