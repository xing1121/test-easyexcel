package com.wdx.easy.util;

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
	 * Excel的第一行转为对象的属性，其他行每行转为一条数据
	 *	@ReturnType	List<T> 
	 *	@Date	2018年9月10日	下午6:54:39
	 *  @Param  @param fieldNames		xlsx中每一列顺序对应实体的属性名，可以为null
	 *  @Param  @param clazz			实体类
	 *  @Param  @param in				输入流
	 *  @Param  @return
	 */
	public static <T> List<T> input2List(List<String> fieldNames, Class<T> clazz, InputStream in) {
		List<T> res = new ArrayList<>();
		ExcelReader reader = new ExcelReader(in, res, new AnalysisEventListener<List<Object>>() {
			@Override
			public void invoke(List<Object> object, AnalysisContext context) {
				T t = ObjectUtils.list2Object(object, null, clazz);
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
		return res;
	}
	
	/**
	 * 对象集合sourceList转输出out
	 *	@ReturnType	void 
	 *	@Date	2020年3月10日	下午5:28:30
	 *  @Param  @param out					输出流
	 *  @Param  @param headers				xlsx文件中第一行（表头）按顺序显示的列名，可以为null
	 *  @Param  @param sourceList			实体集合
	 *  @Param  @param ignoreProperties		忽视的属性名称
	 */
	public static <T> void list2Out(List<String> headers, List<T> sourceList, OutputStream out, List<String> ignoreProperties){
		Instant start = Instant.now();
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
        // 创建sheet
        com.alibaba.excel.metadata.Sheet sheet = new com.alibaba.excel.metadata.Sheet(1, 1);
        sheet.setSheetName("sheet");
        // 表头
        List<List<String>> head = new ArrayList<>(10);
        if (headers != null && headers.size() != 0) {
        	for (String headName : headers) {
        		head.add(Arrays.asList(headName));
        	}
		} else {
			Class<?> clazz = sourceList.get(0).getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				if (ignoreProperties == null || !ignoreProperties.contains(fieldName)) {
					head.add(Arrays.asList(fieldName));
				}
			}
		}
        sheet.setHead(head);
        // 内容
        writer.write1(ObjectUtils.objects2Lists(sourceList, ignoreProperties, false), sheet);
        writer.finish();
        Instant end = Instant.now();
        logger.info("集合转OutputStream转换完成，耗时（ms）->" + Duration.between(start, end).toMillis());
	}
	
	/**
	 * 对象集合sourceList转输出字节数组byte[]
	 *	@ReturnType	byte[] 
	 *	@Date	2020年3月10日	上午9:26:50
	 *  @Param  @param headers
	 *  @Param  @param sourceList
	 *  @Param  @param ignoreProperties
	 *  @Param  @return
	 */
	public static <T> byte[] list2Byte(List<String> headers, List<T> sourceList, List<String> ignoreProperties){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		list2Out(headers, sourceList, bos, ignoreProperties);
		return bos.toByteArray();
	}
	
}
