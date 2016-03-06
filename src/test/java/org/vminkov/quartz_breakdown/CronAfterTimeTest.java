package org.vminkov.quartz_breakdown;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;
import org.quartz.CronTrigger;
import org.quartz.spi.OperableTrigger;


/**
 * This bug was active speaking of
 * Mon Mar 07 01:43:47 EET 2016
 * @author veliko
 *
 */
public class CronAfterTimeTest {
	
	@Test
	public void testDefaultEET() {
		// print basic local time for logs proof
		System.out.println(new Date());
	
		// EET
		TimeZone defaultTimeZone = TimeZone.getDefault();
		System.out.println(defaultTimeZone);
		computeFirstFireTime(defaultTimeZone);
	}
	
	@Test
	public void testPDT() {
		// print basic local time for logs proof
		System.out.println(new Date());
		
		TimeZone currentSanFranTime = TimeZone.getTimeZone("PDT");
		System.out.println(currentSanFranTime);
		computeFirstFireTime(currentSanFranTime);
	}
	
	@Test
	public void testGMTMinus8() {
		// print basic local time for logs proof
		System.out.println(new Date());
		
		TimeZone gmtMinus8 = TimeZone.getTimeZone("GMT-8:00");
		System.out.println(gmtMinus8);
		computeFirstFireTime(gmtMinus8);
	}
	
	@Test
	public void testPST() {
		// print basic local time for logs proof
		System.out.println(new Date());
		
		// HERE IT GOES IN AN ENDLESS LOOP
		TimeZone serverReportedTime = TimeZone.getTimeZone("PST");
		System.out.println(serverReportedTime);
		computeFirstFireTime(serverReportedTime);
	}
	
	private void computeFirstFireTime(TimeZone timezone) {
		timezone.setRawOffset(3600000);
		
		CronTrigger trigger = newTrigger().withIdentity("name", "group").withSchedule(cronSchedule("0 0 18-23/1 ? * 1").inTimeZone(timezone)).startNow().build();
		
		OperableTrigger trig = (OperableTrigger) trigger;
		
		Date ft = trig.computeFirstFireTime(null);
		System.out.println(ft);
	}
}
