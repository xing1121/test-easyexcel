package com.wdx.easy.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述：对象工具类
 * @author 80002888
 * @date   2019年4月11日
 */
public class ObjectUtils {

	private static Logger logger = LoggerFactory.getLogger(ObjectUtils.class);
	
	/**
	 * 对象集合sourceList转List<Objecy>
	 *	@ReturnType	List<List<Object>> 
	 *	@Date	2019年3月25日	下午6:00:05
	 *  @Param  @param sourceList		数据集合
	 *  @Param  @param fieldNames		属性名集合
	 *  @Param  @param keepStatus		指定上述属性名是要保留（true）还是剔除（false）
	 *  @Param  @return
	 */
	public static <T> List<List<Object>> getListObj(List<T> sourceList, List<String> fieldNames, boolean keepStatus){
		List<List<Object>> resList = new ArrayList<>();
		for (T t : sourceList) {
			List<Object> oneList = object2List(t, fieldNames, keepStatus);
			if (oneList != null) {
				resList.add(oneList);
			}
		}
		return resList;
	}
	
	/**
	 * 对象t转List<Object>
	 *	@ReturnType	List<Object> 
	 *	@Date	2019年3月25日	下午5:40:53
	 *  @Param  @param t				数据集合
	 *  @Param  @param fieldNames		属性名集合
	 *  @Param  @param keepStatus		指定上述属性名是要保留（true）还是剔除（false）
	 *  @Param  @return
	 */
	private static <T> List<Object> object2List(T t, List<String> fieldNames, boolean keepStatus){
		// 对象的类型
		Class<? extends Object> clazz = t.getClass();
		// 获取所有属性
		Field[] fields = clazz.getDeclaredFields();
		// 返回值
		List<Object> fieldValueList = new ArrayList<>();
		for (Field field : fields) {
			// 去除serialVersionUID属性
			if ("serialVersionUID".equals(field.getName())) {
				continue;
			}
			// 判断是保留还是剔除
			if (keepStatus) {
				// 保留
				if (fieldNames == null || !fieldNames.contains(field.getName())) {
					continue;
				}
			} else {
				// 剔除
				if (fieldNames != null && fieldNames.contains(field.getName())) {
					continue;
				}
			}
			// 获取属性值
			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(t);
			} catch (Exception e) {
				logger.error("get error->" + t, e);
			}
			// 日期格式化
			if (value != null && value instanceof Date) {
				value = DateUtils.date2Str((Date)value, "yyyy-MM-dd HH:mm:ss");
			}
			// 添加到集合中
			fieldValueList.add(value);
		}
		return fieldValueList;
	}
	
}
