package com.wdx.easy.util;

import java.io.OutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * 描述：Easyexcel测试
 * @author 80002888
 * @date   2019年3月25日
 */
public class EasyexcelUtils {

	/**
	 * 日志打印
	 */
	private static Logger logger = LoggerFactory.getLogger(EasyexcelUtils.class);
	
	/**
	 * 对象集合sourceList转输出out
	 *	@ReturnType	void 
	 *	@Date	2019年3月25日	下午5:28:30
	 *  @Param  @param out					输出流
	 *  @Param  @param headers				xlsx文件中第一行（表头）按顺序显示的列名，可以为null
	 *  @Param  @param sourceList			实体集合
	 *  @Param  @param ignoreProperties		忽视的属性名称
	 */
	public static <T> void export4Output(List<String> headers, List<T> sourceList, OutputStream out, List<String> ignoreProperties){
		Instant start = Instant.now();
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
        // 创建sheet
        com.alibaba.excel.metadata.Sheet sheet = new com.alibaba.excel.metadata.Sheet(1, 1);
        sheet.setSheetName("sheet");
        // 表头
        if (headers != null && headers.size() != 0) {
        	List<List<String>> head = new ArrayList<>(headers.size());
        	for (String headName : headers) {
        		head.add(Arrays.asList(headName));
        	}
        	sheet.setHead(head);
		}
        // 内容
        writer.write1(ObjectUtils.getListObj(sourceList, ignoreProperties, false), sheet);
        writer.finish();
        Instant end = Instant.now();
        logger.info("集合转OutputStream转换完成，耗时（ms）->" + Duration.between(start, end).toMillis());
	}
	
	
}
