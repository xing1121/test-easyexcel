package com.wdx.easy.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.wdx.easy.dto.EasyExcelParamDto;
import com.wdx.easy.reflection.ReflectionClass;

import cn.hutool.core.lang.Assert;

/**
 * 描述：EasyexcelUtil
 * @author 80002888
 * @date   2020年3月10日
 */
public class EasyexcelUtil {

	/**
	 * 日志打印
	 */
	private static Logger logger = LoggerFactory.getLogger(EasyexcelUtil.class);
	
	/**
	 * 输入Excel的byte[]，Excel的第一行转为对象的属性，其他行每行转为一条数据
	 *	@ReturnType	List<T> 
	 *	@Date	2018年9月11日	下午3:33:22
	 *  @Param  @param fieldNames		xlsx中每一列顺序对应实体的属性名，可以为null
	 *  @Param  @param clazz			实体类
	 *  @Param  @param bytes			输入数组
	 *  @Param  @return
	 */
	public static <T> List<T> bytes2List(
			List<String> fieldNames, 
			Class<T> clazz, 
			byte[] bytes) {
		Assert.notNull(clazz);
		Assert.isTrue(bytes != null && bytes.length > 0);
		return input2List(fieldNames, clazz, new ByteArrayInputStream(bytes));
	}
	
	/**
	 * Excel的第一行转为对象的属性，其他行每行转为一条数据
	 *	@ReturnType	List<T> 
	 *	@Date	2018年9月10日	下午6:54:39
	 *  @Param  @param fieldNames		xlsx中每一列顺序对应实体的属性名，可以为null
	 *  @Param  @param clazz			实体类
	 *  @Param  @param in				输入流
	 *  @Param  @return
	 */
	public static <T> List<T> input2List(
			List<String> fieldNames, 
			Class<T> clazz, 
			InputStream in) {
		Assert.notNull(clazz);
		Assert.notNull(in);
        Instant start = Instant.now();
		List<T> res = new ArrayList<>();
		ReflectionClass<T> reflectionClass = new ReflectionClass<>(clazz, true);
		ExcelReader reader = new ExcelReader(in, res, new AnalysisEventListener<List<Object>>() {
			@Override
			public void invoke(List<Object> object, AnalysisContext context) {
				T t = ObjectUtils.listConvertToObj(object, fieldNames, reflectionClass);
				if (t != null) {
					res.add(t);
				}
			}
			@Override
			public void doAfterAllAnalysed(AnalysisContext context) {
			}
		});
        com.alibaba.excel.metadata.Sheet sheet = new com.alibaba.excel.metadata.Sheet(1, 1);
		reader.read(sheet);
        Instant end = Instant.now();
        logger.info("转换完成，耗时（ms）->" + Duration.between(start, end).toMillis());
		return res;
	}
	
	/**
	 * 对象集合sourceList转Excel，输出字节数组byte[]
	 *	@ReturnType	byte[] 
	 *	@Date	2020年3月10日	上午9:26:50
	 *  @Param  @param headers				xlsx文件中第一行（表头）按顺序显示的列名，可以为null
	 *  @Param  @param sourceList			实体集合
	 *  @Param  @param propertyNames		属性名称
	 *  @Param  @param keepFlag				属性保留还是去除
	 *  @Param  @return
	 */
	public static <T> byte[] list2Byte(
			List<String> headers, 
			List<T> sourceList, 
			List<String> propertyNames, 
			boolean keepFlag){
		Assert.notEmpty(sourceList);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		list2Out(bos, headers, sourceList, propertyNames, keepFlag);
		return bos.toByteArray();
	}
	
	/**
	 * 对象集合sourceList转Excel，输出字节数组byte[]
	 *	@ReturnType	void 
	 *	@Date	2020年3月10日	下午5:28:30
	 *  @Param  @param out					输出流
	 *  @Param  @param paramDtoList			参数集合
	 */
	public static byte[] list2Byte(
			OutputStream out, 
			List<EasyExcelParamDto> paramDtoList){
		Assert.notEmpty(paramDtoList);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		list2Out(bos, paramDtoList);
		return bos.toByteArray();
	}
	
	/**
	 * 对象集合sourceList转Excel，输出OutputStream
	 *	@ReturnType	void 
	 *	@Date	2020年3月10日	下午5:28:30
	 *  @Param  @param out					输出流
	 *  @Param  @param paramDtoList			参数集合
	 */
	public static void list2Out(
			OutputStream out, 
			List<EasyExcelParamDto> paramDtoList){
		Assert.notEmpty(paramDtoList);
		Assert.notNull(out);
		Instant start = Instant.now();
        ExcelWriter writer = null;
		try {
			writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
			int sheetNo = 1;
			for (EasyExcelParamDto dto : paramDtoList) {
				String sheetName = dto.getSheetName();
				List<String> headers = dto.getHeaders();
				List<?> sourceList = dto.getSourceList();
				List<String> propertyNames = dto.getPropertyNames();
				boolean keepFlag = dto.isKeepFlag();
				// 创建sheet
				com.alibaba.excel.metadata.Sheet sheet = new com.alibaba.excel.metadata.Sheet(sheetNo ++, 1);
				sheet.setSheetName(sheetName);
				fillSheet(headers, sourceList, propertyNames, keepFlag, writer, sheet);
			}
		} catch (Exception e) {
			logger.error("get error->", e);
		} finally {
			if (writer != null) {
				writer.finish();
			}
		}
        Instant end = Instant.now();
        logger.info("转换完成，耗时（ms）->" + Duration.between(start, end).toMillis());
	}
	
	/**
	 * 对象集合sourceList转Excel，输出OutputStream
	 *	@ReturnType	void 
	 *	@Date	2020年3月10日	下午5:28:30
	 *  @Param  @param out					输出流
	 *  @Param  @param headers				xlsx文件中第一行（表头）按顺序显示的列名，可以为null
	 *  @Param  @param sourceList			实体集合
	 *  @Param  @param propertyNames		属性名称
	 *  @Param  @param keepFlag				属性保留还是去除
	 */
	public static <T> void list2Out(
			OutputStream out, 
			List<String> headers, 
			List<T> sourceList, 
			List<String> propertyNames, 
			boolean keepFlag){
		Assert.notEmpty(sourceList);
		Assert.notNull(out);
		Instant start = Instant.now();
        ExcelWriter writer = null;
		try {
			writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
			// 创建sheet1
			com.alibaba.excel.metadata.Sheet sheet = new com.alibaba.excel.metadata.Sheet(1, 1);
			sheet.setSheetName("sheet01");
			fillSheet(headers, sourceList, propertyNames, keepFlag, writer, sheet);
		} catch (Exception e) {
			logger.error("get error->", e);
		} finally {
			if (writer != null) {
				writer.finish();
			}
		}
        Instant end = Instant.now();
        logger.info("转换完成，耗时（ms）->" + Duration.between(start, end).toMillis());
	}

	private static <T> void fillSheet(List<String> headers, List<T> sourceList, List<String> propertyNames,
			boolean keepFlag, ExcelWriter writer, com.alibaba.excel.metadata.Sheet sheet) {
		// 表头
		List<List<String>> head = new ArrayList<>(10);
		if (headers != null && headers.size() != 0) {
			// 使用参数当表头
			for (String headName : headers) {
				head.add(Arrays.asList(headName));
			}
		} else {
			// 使用默认表头
			Class<?> clazz = sourceList.get(0).getClass();
			List<Field> fieldList = ReflectionUtils.getFieldList(clazz);
			// 保留还是去除
			if (keepFlag) {
				// 参数属性要保留
				for (String propertyName : propertyNames) {
					head.add(Arrays.asList(propertyName));
				}
			} else {
				// 参数属性要去除
				for (Field field : fieldList) {
					String fieldName = field.getName();
					if (propertyNames != null 
							&& propertyNames.contains(field.getName())) {
						continue;
					}
					// TODO 剔除serialVersionUID
					if ("serialVersionUID".equals(fieldName)) {
						continue;
					}
					head.add(Arrays.asList(fieldName));
				}
			}
		}
		// 内容
		sheet.setHead(head);
		writer.write1(ObjectUtils.objects2Lists(sourceList, propertyNames, keepFlag), sheet);
	}
	
}
