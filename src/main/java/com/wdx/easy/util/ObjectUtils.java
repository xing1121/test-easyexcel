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
	 * 把sourceList中每一个List<Object>转为对象T，返回List<T>
	 *	@ReturnType	List<T> 
	 *	@Date	2020年3月10日	下午5:42:21
	 *  @Param  @param sourceList		数据集合
	 *  @Param  @param fieldNames		List<Object>中按顺序对应对象T的属性名
	 *  @Param  @param clazz			对象的类
	 *  @Param  @return
	 */
	public static <T> List<T> lists2Objects(List<List<Object>> sourceList, List<String> fieldNames, Class<T> clazz){
		List<T> resList = new ArrayList<>();
		for (List<Object> x : sourceList) {
			T obj = list2Object(x, fieldNames, clazz);
			if (obj != null) {
				resList.add(obj);
			}
		}
		return resList;
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
	 *  @Param  @param t				对象
	 *  @Param  @param fieldNames		属性名集合
	 *  @Param  @param keepStatus		指定上述属性名是要保留（true）还是剔除（false）
	 *  @Param  @return
	 */
	public static <T> List<Object> object2List(T t, List<String> fieldNames, boolean keepStatus){
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
				value = DateUtils.date2String((Date)value, "yyyy-MM-dd HH:mm:ss");
			}
			// 添加到集合中
			fieldValueList.add(value);
		}
		return fieldValueList;
	}
	
	/**
	 * List<Object>转对象t
	 *	@ReturnType	List<T> 
	 *	@Date	2020年3月10日	下午5:46:19
	 *  @Param  @param x				数据集合
	 *  @Param  @param fieldNames		List<Object>中按顺序对应对象T的属性名
	 *  @Param  @param clazz			类名
	 *  @Param  @return
	 */
	public static <T> T list2Object(List<Object> x, List<String> fieldNames, Class<T> clazz) {
		T t = null;
		try {
			if (fieldNames == null || fieldNames.size() == 0) {
				fieldNames = new ArrayList<>(x.size());
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					fieldNames.add(field.getName());
				}
			}
			t = clazz.newInstance();
			for (int i = 0; i < x.size(); i++) {
				// 获取值
				Object text = x.get(i);
				// 获取属性
				String fieldName = fieldNames.get(i);
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				if (text == null) {
					field.set(t, null);
				} else {
					String value = (String)text;
					Class<?> fieldType = field.getType();
					if (Date.class.isAssignableFrom(fieldType)) {
						// 日期格式化解析
						Date date = null;
						if (value.contains(":")) {
							date = DateUtils.string2Date(value.toString(), "yyyy-MM-dd HH:mm:ss");
						} else {
							date = DateUtils.string2Date(value.toString(), "yyyy-MM-dd");
						}
						field.set(t, date);
					} else if(Byte.class.isAssignableFrom(fieldType)) {
	                    field.set(t, Integer.valueOf(value));
					} else if(Short.class.isAssignableFrom(fieldType)) {
						field.set(t, Short.valueOf(value));
					} else if(Character.class.isAssignableFrom(fieldType)) {
						field.set(t, Character.valueOf(value.charAt(0)));
					} else if(Integer.class.isAssignableFrom(fieldType)) {
						field.set(t, Integer.valueOf(value));
	                } else if(Long.class.isAssignableFrom(fieldType)) {
	                    field.set(t, Long.valueOf(value));
	                } else if(Float.class.isAssignableFrom(fieldType)) {
	                	field.set(t, Float.valueOf(value));
	                } else if(Double.class.isAssignableFrom(fieldType)) {
	                    field.set(t, Double.valueOf(value));
	                } else {
	                    field.set(t, value);
	                }
				}
			}
		} catch (Exception e) {
			logger.error("get error->" + clazz, e);
		}
		return t;
	}
	
}
