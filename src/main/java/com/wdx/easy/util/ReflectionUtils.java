package com.wdx.easy.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	public static Field getField(Class<?> clazz, String fieldName){
		Field field = null;
		while (field == null && clazz != null){
			try {
				field = clazz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				logger.warn(clazz + " no such field->" + fieldName, e);
			}
			clazz = clazz.getSuperclass();
		}
		return field;
	}
	
	/**
	 * 获取所有属性（包括父类）
	 *	@ReturnType	Field[] 
	 *	@Date	2020年3月30日	上午10:21:17
	 *  @Param  @param clazz
	 *  @Param  @return
	 */
	public static Field[] getAllFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null){
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
	}
	
	/**
	 * 获取所有属性（包括父类）
	 *	@ReturnType List<Field> 
	 *	@Date	2020年3月30日	上午10:21:17
	 *  @Param  @param clazz
	 *  @Param  @return
	 */
	public static List<Field> getAllFieldsList(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null){
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		return fieldList;
	}
	
}
