package com.wdx.easy.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述：ReflectionUtils
 * @author 80002888
 * @date   2020年3月30日
 */
public class ReflectionUtils {

	private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
	
	/**
	 * 获取属性（包括父类）
	 *	@ReturnType	Field 
	 *	@Date	2020年4月3日	下午6:15:00
	 *  @Param  @param clazz
	 *  @Param  @param fieldName
	 *  @Param  @return
	 */
	public static Field getField(Class<?> clazz, String fieldName) {
		Field field = null;
		while (field == null && clazz != null){
			try {
				field = clazz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				logger.warn(clazz + " no such field->" + fieldName);
			}
			clazz = clazz.getSuperclass();
		}
		return field;
	}
	
	/**
	 * 获取方法（包括父类）
	 *	@ReturnType	Method 
	 *	@Date	2020年4月20日	上午8:06:52
	 *  @Param  @param clazz
	 *  @Param  @param methodName
	 *  @Param  @param parameterTypes
	 *  @Param  @return
	 */
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
		Method method = null;
		while (method == null && clazz != null){
			try {
				method = clazz.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
				logger.warn(clazz + " no such method->" + methodName);
			}
			clazz = clazz.getSuperclass();
		}
		return method;
	}
	
	/**
	 * 获取所有属性（包括父类）
	 *	@ReturnType	Field[] 
	 *	@Date	2020年3月30日	上午10:21:17
	 *  @Param  @param clazz
	 *  @Param  @return
	 */
	public static Field[] getFieldArray(Class<?> clazz) {
		List<Field> fieldsList = getFieldList(clazz);
		Field[] fields = new Field[fieldsList.size()];
		fieldsList.toArray(fields);
		return fields;
	}
	
	/**
	 * 获取所有属性（包括父类）
	 *	@ReturnType List<Field> 
	 *	@Date	2020年3月30日	上午10:21:17
	 *  @Param  @param clazz
	 *  @Param  @return
	 */
	public static List<Field> getFieldList(Class<?> clazz) {
		List<Field> fieldsList = new ArrayList<>(100);
		while (clazz != null){
			fieldsList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		return fieldsList;
	}
	
	/**
	 * 获取所有属性（包括父类）的Map（K=V：fieldName=Field）
	 *	@ReturnType List<Field> 
	 *	@Date	2020年3月30日	上午10:21:17
	 *  @Param  @param clazz
	 *  @Param  @return
	 */
	public static Map<String, Field> getFieldMap(Class<?> clazz) {
		Map<String, Field> fieldsMap = new HashMap<>(100);
		while (clazz != null){
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				fieldsMap.put(field.getName(), field);
			}
			clazz = clazz.getSuperclass();
		}
		return fieldsMap;
	}
	
}
