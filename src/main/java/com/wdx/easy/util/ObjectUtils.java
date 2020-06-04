package com.wdx.easy.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.annotation.JSONField;
import com.wdx.easy.reflection.ReflectionClass;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Assert;

/**
 * 描述：对象工具类
 * @author 80002888
 * @date   2019年4月11日
 */
@SuppressWarnings(value = {"unchecked"})
public class ObjectUtils {

	private static Logger logger = LoggerFactory.getLogger(ObjectUtils.class);
	
	/**
	 * 把sourceList中每一个List<Object>转为对象T，返回List<T>
	 *	@ReturnType	List<T> 
	 *	@Date	2020年3月10日	下午5:42:21
	 *  @Param  @param dataList			数据集合（对应对象集合List<T>）
	 *  @Param  @param fieldNames		List<Object>中按顺序对应对象T的属性名
	 *  @Param  @param clazz			对象的类
	 *  @Param  @return
	 */
	public static <T> List<T> lists2Objects(List<List<Object>> dataList, List<String> fieldNames, Class<T> clazz){
		Assert.notEmpty(dataList);
		Assert.notNull(clazz);
		ReflectionClass<T> reflectionClass = new ReflectionClass<>(clazz, true);
		List<T> resList = new ArrayList<>();
		for (List<Object> data : dataList) {
			T obj = listConvertToObj(data, fieldNames, reflectionClass);
			if (obj != null) {
				resList.add(obj);
			}
		}
		return resList;
	}
	
	/**
	 * List<Object>转对象t
	 *	@ReturnType	List<T> 
	 *	@Date	2020年3月10日	下午5:46:19
	 *  @Param  @param data				数据（对应一个对象T）
	 *  @Param  @param fieldNames		List<Object>中按顺序对应对象T的属性名
	 *  @Param  @param clazz			类名
	 *  @Param  @return
	 */
	public static <T> T list2Object(List<Object> data, List<String> fieldNames, Class<T> clazz) {
		Assert.notEmpty(data);
		Assert.notNull(clazz);
		ReflectionClass<T> reflectionClass = new ReflectionClass<>(clazz, true);
		return listConvertToObj(data, fieldNames, reflectionClass);
	}
	
	/**
	 * List<Object>转换为T
	 *	@ReturnType	T 
	 *	@Date	2020年4月20日	上午10:03:04
	 *  @Param  @param data				数据集合
	 *  @Param  @param fieldNames		每个数据对于的属性名，可以为空，默认按顺序使用所有属性
	 *  @Param  @param reflectionClass	反射类
	 *  @Param  @return
	 */
	public static <T> T listConvertToObj(
			List<Object> data, 
			List<String> fieldNames, 
			ReflectionClass<T> reflectionClass) {
		Assert.notEmpty(data);
		Assert.notNull(reflectionClass);
		Class<T> clazz = reflectionClass.getClazz();
		try {
			// 要转换的属性集合
			List<Field> keepFields = null;
			if (CollectionUtils.isNotEmpty(fieldNames)) {
				// 使用参数中的属性转换
				keepFields = new ArrayList<>(fieldNames.size());
				for (String fieldName : fieldNames) {
					Field field = reflectionClass.getField(fieldName);
					keepFields.add(field);
				}
			} else {
				// 使用默认所有属性转换
				keepFields = reflectionClass.getFieldList();
				// TODO 剔除serialVersionUID
				keepFields.removeIf(x->"serialVersionUID".equals(x.getName()));
			}
			T t = clazz.newInstance();
			for (int i = 0; i < data.size(); i++) {
				// 获取值
				Object text = data.get(i);
				if (text == null) {
					continue;
				}
				String value = (String)text;
				// 获取属性
				Field field = keepFields.get(i);
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				// 解析属性类型，并为对象属性赋值
				Class<?> fieldType = field.getType();
				String fieldTypeName = fieldType.getSimpleName();
				if (String.class.isAssignableFrom(fieldType)) {
					field.set(t, value);
				} else if(Byte.class.isAssignableFrom(fieldType)
						|| byte.class.getSimpleName().equals(fieldTypeName)) {
					field.set(t, Integer.valueOf(value));
				} else if(Short.class.isAssignableFrom(fieldType)
						|| short.class.getSimpleName().equals(fieldTypeName)) {
					field.set(t, Short.valueOf(value));
				} else if(Character.class.isAssignableFrom(fieldType)
						|| char.class.getSimpleName().equals(fieldTypeName)) {
					field.set(t, Character.valueOf(value.charAt(0)));
				} else if(Integer.class.isAssignableFrom(fieldType)
						|| int.class.getSimpleName().equals(fieldTypeName)) {
					field.set(t, Integer.valueOf(value));
				} else if(Long.class.isAssignableFrom(fieldType)
						|| long.class.getSimpleName().equals(fieldTypeName)) {
					field.set(t, Long.valueOf(value));
				} else if(Float.class.isAssignableFrom(fieldType)
						|| float.class.getSimpleName().equals(fieldTypeName)) {
					field.set(t, Float.valueOf(value));
				} else if(Double.class.isAssignableFrom(fieldType)
						|| double.class.getSimpleName().equals(fieldTypeName)) {
					field.set(t, Double.valueOf(value));
				} else if(Boolean.class.isAssignableFrom(fieldType)
						|| boolean.class.getSimpleName().equals(fieldTypeName)) {
					field.set(t, Boolean.valueOf(value));
				} else if (Date.class.isAssignableFrom(fieldType) 
						|| Date.class.getSimpleName().equals(fieldTypeName)) {
					// 日期格式化解析
					// 如果该属性或get方法包含@JSONField注解，则使用其格式
					JSONField annotation = field.getAnnotation(JSONField.class);
					if (annotation == null) {
						String methodName = "get" + toUpperCaseFirstOne(field.getName());
						Method method = reflectionClass.getNoParamMethod(methodName);
						annotation = AnnotationUtil.getAnnotation(method, JSONField.class);
					}
					String format = null;
					if (annotation != null) {
						format = annotation.format();
					} else if (value.contains(":")) {
						format = "yyyy-MM-dd HH:mm:ss";
					} else {
						format = "yyyy-MM-dd";
					}
					field.set(t, DateUtils.string2Date(value.toString(), format));
				} else {
					field.set(t, value);
				}
			}
			return t;
		} catch (Exception e) {
			logger.error("get error->" + clazz, e);
		}
		return null;
	}
	
	/**
	 * 把sourceList中每一个对象T转为List<Object>，返回List<List<Object>>
	 *	@ReturnType	List<List<Object>> 
	 *	@Date	2019年3月25日	下午6:00:05
	 *  @Param  @param sourceList		数据集合
	 *  @Param  @param fieldNames		属性名集合
	 *  @Param  @param keepStatus		指定上述属性名是要保留（true）还是剔除（false）
	 *  @Param  @return
	 */
	public static <T> List<List<Object>> objects2Lists(List<T> sourceList, List<String> fieldNames, boolean keepStatus){
		Assert.notEmpty(sourceList);
		List<List<Object>> resList = new ArrayList<>();
		Class<T> clazz = (Class<T>) sourceList.get(0).getClass();
		ReflectionClass<T> reflectionClass = new ReflectionClass<>(clazz, true);
		for (T t : sourceList) {
			List<Object> signleList = objConvertToList(t, fieldNames, keepStatus, reflectionClass);
			if (signleList != null) {
				resList.add(signleList);
			}
		}
		return resList;
	}
	
	/**
	 * 对象t转List<Object>
	 *	@ReturnType	List<Object> 
	 *	@Date	2019年3月25日	下午5:40:53
	 *  @Param  @param t				对象
	 *  @Param  @param fieldNames		属性名集合
	 *  @Param  @param keepStatus		指定上述属性名是要保留（true）还是剔除（false）
	 *  @Param  @return
	 */
	public static <T> List<Object> object2List(T t, List<String> fieldNames, boolean keepStatus) {
		Assert.notNull(t);
		Class<T> clazz = (Class<T>) t.getClass();
		ReflectionClass<T> reflectionClass = new ReflectionClass<>(clazz, true);
		return objConvertToList(t, fieldNames, keepStatus, reflectionClass);
	}
	
	/**
	 * T转换为List<Object>
	 *	@ReturnType	List<Object> 
	 *	@Date	2020年4月20日	上午10:08:42
	 *  @Param  @param t					对象
	 *  @Param  @param fieldNames			属性名集合
	 *  @Param  @param keepStatus			属性名集合是要保留的还是去掉的
	 *  @Param  @param reflectionClass		反射类
	 *  @Param  @return
	 */
	public static <T> List<Object> objConvertToList(
			T t, 
			List<String> fieldNames, 
			boolean keepStatus,
			ReflectionClass<T> reflectionClass) {
		List<Object> resList = new ArrayList<>();
		List<Field> keepFields = new ArrayList<>(100);
		if (keepStatus) {
			// 参数fieldNames为要保留的属性名
			if (CollectionUtils.isNotEmpty(fieldNames)) {
				for (String fieldName : fieldNames) {
					Field field = reflectionClass.getField(fieldName);
					keepFields.add(field);
				}
			}
		} else {
			// 参数fieldNames为要剔除的属性名
			// 获取所有属性
			List<Field> allFields = reflectionClass.getFieldList();
			for (Field field : allFields) {
				String fieldName = field.getName();
				// TODO 剔除serialVersionUID
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				}
				// 剔除
				if (fieldNames != null && fieldNames.contains(fieldName)) {
					continue;
				}
				// 保留
				keepFields.add(field);
			}
		}
		// 循环获取每个要保留的属性的值，封装进fieldValueList
		for (Field field : keepFields) {
			// 获取属性值
			Object value = null;
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			try {
				value = field.get(t);
			} catch (Exception e) {
				logger.error("get error->" + t, e);
			}
			// 判断如果是日期，则日期格式化
			boolean dateFlag = value != null 
					&& (value instanceof Date
						|| Date.class.isAssignableFrom(field.getType()) 
						|| Date.class.getSimpleName().equals(field.getType().getSimpleName()));
			if (dateFlag) {
				try {
					JSONField annotation = field.getAnnotation(JSONField.class);
					if (annotation == null) {
						String methodName = "get" + toUpperCaseFirstOne(field.getName());
						Method method = reflectionClass.getNoParamMethod(methodName);
						annotation = method.getAnnotation(JSONField.class);
					}
					String format = annotation == null ? "yyyy-MM-dd HH:mm:ss" : annotation.format();
					value = DateUtils.date2String((Date)value, format);
				} catch (Exception e) {
					logger.error("get error->" + t, e);
				}
			}
			// Long类型转int值
			boolean longFlag = value != null 
					&& value instanceof Long;
			if (longFlag) {
				value = ((Long)value).intValue();
			}
			// 添加到集合中
			resList.add(value);
		}
		return resList;
	}

	/**
	 * 首字母转大写
	 *	@ReturnType	String 
	 *	@Date	2020年4月17日	下午5:08:02
	 *  @Param  @param s
	 *  @Param  @return
	 */
	private static String toUpperCaseFirstOne(String s) {
		Assert.notNull(s);
		if (Character.isUpperCase(s.charAt(0))) {
			return s;
		} else {
			return String.valueOf(Character.toUpperCase(s.charAt(0))) + s.substring(1);
		}
	}
	
}
