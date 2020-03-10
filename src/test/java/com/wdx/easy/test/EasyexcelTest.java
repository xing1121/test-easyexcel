package com.wdx.easy.test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wdx.easy.domain.Cat;
import com.wdx.easy.domain.Person;
import com.wdx.easy.util.DateUtils;
import com.wdx.easy.util.EasyexcelUtils;

/**
 * 描述：测试Easyexcel
 * @author 80002888
 * @date   2019年3月26日
 */
public class EasyexcelTest {

	private static Logger logger = LoggerFactory.getLogger(EasyexcelTest.class);
	
	private static String DATE_PATTERN = "yyyy-MM-dd";
	
	private static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 测试集合转输出流
	 *	@ReturnType	void 
	 *	@Date	2019年3月26日	上午9:35:53
	 *  @Param
	 */
	@Test
	public void test2(){
		try {
			// 数据
			List<Cat> cats = Arrays.asList(
				new Cat(1L, "汤姆", 12.53F, '公'),
				new Cat(2L, "兔子", 12F, '母'),
				new Cat(3L, "大象", 153.000F, '公')
			);
			// 标题
			List<String> headers = Arrays.asList("ID", "猫的名", "重量", "SEX");
			// 转为文件输出
			EasyexcelUtils.export4Output(headers, cats, new FileOutputStream(new File("D:/user/80002888/桌面/poi.xlsx")), null);
		} catch (Exception e) {
			logger.error("get error->", e);
		}
	}
	
	/**
	 * 测试集合转输出流
	 *	@ReturnType	void 
	 *	@Date	2019年3月26日	上午9:35:50
	 *  @Param
	 */
	@Test
	public void test1() {
		try {
			// 数据
			List<Person> persons = Arrays.asList(
				new Person(null, "张三", 18000, 
						DateUtils.string2Date("1998-08-25", DATE_PATTERN), 
						DateUtils.string2Date("1998-08-25 14:22:55", DATE_TIME_PATTERN), 
						true, 5555.55),
				new Person(2L, null, 20000, 
						DateUtils.string2Date("1997-05-08", DATE_PATTERN), 
						DateUtils.string2Date("1997-05-08 08:19:57", DATE_TIME_PATTERN), 
						false, 8866.88),
				new Person(3L, "王五", null, 
						DateUtils.string2Date("2000-03-25", DATE_PATTERN), 
						DateUtils.string2Date("2000-03-25 09:39:45", DATE_TIME_PATTERN), 
						true, 3850.0),
				new Person(4L, "赵六", 33000, 
						null, 
						DateUtils.string2Date("2018-12-15 16:10:33", DATE_TIME_PATTERN), 
						true, 9888.15),
				new Person(5L, "田七", 37000, 
						DateUtils.string2Date("2016-01-21", DATE_PATTERN), 
						DateUtils.string2Date("2016-01-21 21:39:15", DATE_TIME_PATTERN), 
						null, 12255.01),
				new Person(6L, "康八", 87000, 
						DateUtils.string2Date("2008-11-30", DATE_PATTERN), 
						DateUtils.string2Date("2008-11-30 05:55:28", DATE_TIME_PATTERN), 
						true, null)
			);
			// 标题
			List<String> headers = Arrays.asList("姓名", "年龄", "出生日期", "生日", "是否有工作", "工资");
			// 转为文件输出
			EasyexcelUtils.export4Output(headers, persons, new FileOutputStream(new File("D:/user/80002888/桌面/poi.xlsx")), Arrays.asList("id"));
		} catch (Exception e) {
			logger.error("get error->", e);
		}
	}
	
}
