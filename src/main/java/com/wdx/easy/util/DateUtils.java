package com.wdx.easy.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述：时间工具类
 * @author 80002888
 * @date   2018年9月10日
 */
public class DateUtils {

	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	/**
	 * 锁
	 */
	private static Lock lock = new ReentrantLock();
	
	/**
	 * 字符串转日期
	 *	@ReturnType	Date 
	 *	@Date	2018年9月10日	下午5:32:09
	 *  @Param  @param str
	 *  @Param  @param pattern
	 *  @Param  @return
	 */
	public static Date string2Date(String dateStr, String pattern){
		lock.lock();
		try {
			if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(pattern)) {
				return null;
			}
			return new SimpleDateFormat(pattern).parse(dateStr);
		} catch (Exception e) {
			logger.error("get error->", e);
		} finally {
			lock.unlock();
		}
		return null;
	}
	
	/**
	 * 日期转字符串
	 *	@ReturnType	Date 
	 *	@Date	2018年9月10日	下午5:35:19
	 *  @Param  @param date
	 *  @Param  @param pattern
	 *  @Param  @return
	 */
	public static String date2String(Date date, String pattern){
		lock.lock();
		try {
			if (date == null || StringUtils.isBlank(pattern)) {
				return null;
			}
			return new SimpleDateFormat(pattern).format(date);
		} catch (Exception e) {
			logger.error("get error->", e);
		} finally {
			lock.unlock();
		}
		return null;
	}
	
}
