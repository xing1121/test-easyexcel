package com.wdx.easy.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wdx.easy.domain.Person2020;
import com.wdx.easy.dto.EasyExcelParamDto;
import com.wdx.easy.util.DateUtils;
import com.wdx.easy.util.EasyexcelUtil;

/**
 * 描述：测试Easyexcel
 * @author 80002888
 * @date   2019年3月26日
 */
public class EasyexcelTest {

	private static Logger logger = LoggerFactory.getLogger(EasyexcelTest.class);
	
	private static String DATE_PATTERN = "yyyy-MM-dd";
	
	private static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	private static final String FILE_PATH = "D:/user/80002888/desktop/easyExcelTest.xlsx";
	
	
	/**
	 * 测试导出4
	 *	@ReturnType	void 
	 *	@Date	2019年3月26日	上午9:35:50
	 *  @Param
	 */
	@Test
	public void testExport4() {
		try {
			// 构造数据
			List<EasyExcelParamDto> paramDtoList = Arrays.asList(
					new EasyExcelParamDto(
							"sheet0001", 
							Arrays.asList("工作名称", "入职日期", "姓名", "年龄", "生日", "是否有工作", "工资"), 
							getDatas(0), 
							Arrays.asList("id", "birthDay", "companyName"), 
							false),
					new EasyExcelParamDto(
							"sheet0002", 
							Arrays.asList("ID", "生日快乐", "公司规范名称"), 
							getDatas(100), 
							Arrays.asList("id", "birthDay", "companyName"), 
							true)
					);
			// 转为文件输出
			EasyexcelUtil.list2Out(
					new FileOutputStream(new File(FILE_PATH)), 
					paramDtoList);
		} catch (Exception e) {
			logger.error("get error->", e);
		}
	}
	
	/**
	 * 测试导入大量数据
	 *	@ReturnType	void 
	 *	@Date	2018年9月11日	下午4:19:48
	 *  @Param
	 */
	@Test
	public void testImport3(){
		try {
			long start = System.currentTimeMillis();
			List<Person2020> list = EasyexcelUtil.input2List(
					null, 
					Person2020.class, 
					new FileInputStream(new File(FILE_PATH)));
			list.forEach(System.out::println);
			long end = System.currentTimeMillis();
			System.out.println("耗时：" + (end - start)/1000 + "s");
		} catch (Exception e) {
			logger.error("get error->", e);
		}
	}
	
	/**
	 * 测试导出大量数据
	 *	@ReturnType	void 
	 *	@Date	2018年9月11日	下午4:19:48
	 *  @Param
	 */
	@Test
	public void testExport3(){
		try {
			long start = System.currentTimeMillis();
			// 数据
			List<Person2020> datas = getDatas(1000000);
			System.out.println("数据大小：" + datas.size());
			EasyexcelUtil.list2Out(
					new FileOutputStream(new File(FILE_PATH)), 
					null, 
					datas, null, false);
			long end = System.currentTimeMillis();
			System.out.println("耗时：" + (end - start)/1000 + "s");
		} catch (Exception e) {
			logger.error("get error->", e);
		}
	}
	
	/**
	 * 测试导入2
	 *	@ReturnType	void 
	 *	@Date	2018年9月10日	下午5:48:12
	 *  @Param
	 */
	@Test
	public void testImport2(){
		try {
			// xlsx中每一列顺序对应实体的属性名
			List<String> fieldNames = Arrays.asList(
					"companyName",
					"id", 
					"birthDay");
			List<Person2020> list = EasyexcelUtil.input2List(
					fieldNames, 
					Person2020.class, 
					new FileInputStream(new File(FILE_PATH)));
			list.forEach(System.out::println);
		} catch (Exception e) {
			logger.error("get error->", e);
		}
	}
	
	/**
	 * 测试导出2
	 *	@ReturnType	void 
	 *	@Date	2019年3月26日	上午9:35:50
	 *  @Param
	 */
	@Test
	public void testExport2() {
		try {
			// 数据
			List<Person2020> persons = getDatas(0);
			// 转为文件输出
			EasyexcelUtil.list2Out(new FileOutputStream(new File(FILE_PATH)), null, persons, Arrays.asList("companyName", "id", "birthDay"), true);
		} catch (Exception e) {
			logger.error("get error->", e);
		}
	}
	
	/**
	 * 测试导入1
	 *	@ReturnType	void 
	 *	@Date	2018年9月10日	下午5:48:12
	 *  @Param
	 */
	@Test
	public void testImport1(){
		try {
			// xlsx中每一列顺序对应实体的属性名
			List<String> fieldNames = Arrays.asList(
					"jobName",
					"jobDate",
					"name", 
					"age",
					"birthDayTime",
					"workStatus",
					"salary");
			List<Person2020> list = EasyexcelUtil.input2List(
					fieldNames, 
					Person2020.class, 
					new FileInputStream(new File(FILE_PATH)));
			list.forEach(System.out::println);
		} catch (Exception e) {
			logger.error("get error->", e);
		}
	}
	
	/**
	 * 测试导出1
	 *	@ReturnType	void 
	 *	@Date	2019年3月26日	上午9:35:50
	 *  @Param
	 */
	@Test
	public void testExport1() {
		try {
			// 数据
			List<Person2020> persons = getDatas(0);
			// 标题
			List<String> headers = Arrays.asList("工作名称", "入职日期", "姓名", "年龄", "生日", "是否有工作", "工资");
			// 转为文件输出
			EasyexcelUtil.list2Out(new FileOutputStream(new File(FILE_PATH)), headers, persons, Arrays.asList("id", "birthDay", "companyName"), false);
		} catch (Exception e) {
			logger.error("get error->", e);
		}
	}
	
	/**
	 * 获取数据集合
	 *	@ReturnType	List<Person> 
	 *	@Date	2020年4月17日	下午6:53:21
	 *  @Param  @param size
	 *  @Param  @return
	 */
	private List<Person2020> getDatas(int size){
		// 数据
		List<Person2020> datas = new ArrayList<>(size * 2);
		// 默认数据
		List<Person2020> defaultDatas = Arrays.asList(
			new Person2020(null, "张三", 18000, 
					DateUtils.string2Date("1998-08-25", DATE_PATTERN), 
					DateUtils.string2Date("1998-08-25 14:22:55", DATE_TIME_PATTERN), 
					true, 5555.55,
					"屌丝程序猿", "阿里巴巴与四十大盗",
					DateUtils.string2Date("1998-08-25 14:22:55", DATE_TIME_PATTERN)),
			new Person2020(2L, null, 20000, 
					DateUtils.string2Date("1997-05-08", DATE_PATTERN), 
					DateUtils.string2Date("1997-05-08 08:19:57", DATE_TIME_PATTERN), 
					false, 8866.88,
					"苦逼设计师", "阿里巴巴与四十大盗",
					DateUtils.string2Date("1997-08-25 14:22:55", DATE_TIME_PATTERN)),
			new Person2020(3L, "王五", null, 
					DateUtils.string2Date("2000-03-25", DATE_PATTERN), 
					DateUtils.string2Date("2000-03-25 09:39:45", DATE_TIME_PATTERN), 
					true, 3850.0,
					"游戏体验师", "阿里巴巴与四十大盗",
					DateUtils.string2Date("2000-08-25 14:22:55", DATE_TIME_PATTERN)),
			new Person2020(4L, "赵六", 33000, 
					null, 
					DateUtils.string2Date("2018-12-15 16:10:33", DATE_TIME_PATTERN), 
					true, 9888.15,
					"加班策划狗", "阿里巴巴与四十大盗",
					DateUtils.string2Date("2018-08-25 14:22:55", DATE_TIME_PATTERN)),
			new Person2020(5L, "田七", 37000, 
					DateUtils.string2Date("2016-01-21", DATE_PATTERN), 
					DateUtils.string2Date("2016-01-21 21:39:15", DATE_TIME_PATTERN), 
					null, 12255.01,
					"需求分析师", "阿里巴巴与四十大盗",
					DateUtils.string2Date("2016-08-25 14:22:55", DATE_TIME_PATTERN)),
			new Person2020(6L, "康八", 87000, 
					DateUtils.string2Date("2008-11-30", DATE_PATTERN), 
					DateUtils.string2Date("2008-11-30 05:55:28", DATE_TIME_PATTERN), 
					true, null,
					"码农鼓励员", "阿里巴巴与四十大盗",
					DateUtils.string2Date("2008-08-25 14:22:55", DATE_TIME_PATTERN))
		);
		datas.addAll(defaultDatas);
		for (int i = 0; i < size; i++) {
			datas.add(new Person2020((long)i, "Swot" + i, i, 
					DateUtils.string2Date("2008-11-30", DATE_PATTERN), 
					DateUtils.string2Date("2008-11-30 05:55:28", DATE_TIME_PATTERN), 
					true, 0.01 + i,
					"保安", "KTV",
					DateUtils.string2Date("2008-08-25 14:22:55", DATE_TIME_PATTERN)));
		}
		return datas;
	}
	
}
