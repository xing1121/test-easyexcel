package com.wdx.easy.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述：ReflectionClass
 * @author 80002888
 * @date   2020年4月20日
 */
public class ReflectionClass<T> {
	
	private static Logger logger = LoggerFactory.getLogger(ReflectionClass.class);

	/**
	 * 是否包含父类，默认false
	 */
	private boolean containSuperClassFlag;
	
	private Class<T> clazz;
	
	private Map<String, Field> fieldMap;
	
	private List<Field> fieldList;
	
	private Field[] fieldArray;
	
	private Map<String, Method> noParamMethodMap;
	
	private List<Method> methodList;
	
	private Method[] methodArray;
	
	public ReflectionClass(Class<T> clazz, boolean containSuperClassFlag) {
		this.clazz = clazz;
		this.containSuperClassFlag = containSuperClassFlag;
		this.initField();
		logger.info("initField finish...");
		this.initMethod();
		logger.info("initMethod finish...");
	}

	/**
	 * 初始化fieldMap、fieldList、fieldArray
	 *	@ReturnType	void 
	 *	@Date	2020年4月20日	上午11:41:39
	 *  @Param
	 */
	private void initField( ) {
		fieldMap = new HashMap<>(100);
		fieldList = new ArrayList<>(100);
		Class<?> cTemp = clazz;
		
		do {
			Field[] fields = cTemp.getDeclaredFields();
			for (Field field : fields) {
				fieldMap.put(field.getName(), field);
				fieldList.add(field);
			}
			cTemp = cTemp.getSuperclass();
		} while (containSuperClassFlag && cTemp != null);
		
		fieldArray = new Field[fieldList.size()];
		fieldList.toArray(fieldArray);
	}
	
	/**
	 * 初始化methodMap、methodList、methodArray
	 *	@ReturnType	void 
	 *	@Date	2020年4月20日	上午11:39:25
	 *  @Param
	 */
	private void initMethod() {
		noParamMethodMap = new HashMap<>(100);
		methodList = new ArrayList<>(100);
		Class<?> cTemp = clazz;
		
		do {
			Method[] methods = cTemp.getDeclaredMethods();
			for (Method method : methods) {
				int parameterCount = method.getParameterCount();
				if (parameterCount == 0) {
					noParamMethodMap.put(method.getName(), method);
				}
				methodList.add(method);
			}
			cTemp = cTemp.getSuperclass();
		} while (containSuperClassFlag && cTemp != null);
		
		methodArray = new Method[methodList.size()];
		methodList.toArray(methodArray);
	}
	
	/**
	 * 是否包含父类
	 *	@ReturnType	boolean 
	 *	@Date	2020年4月20日	上午11:42:21
	 *  @Param  @return
	 */
	public boolean isContainSuperClassFlag() {
		return containSuperClassFlag;
	}

	/**
	 * 获取类
	 *	@ReturnType	Class<T> 
	 *	@Date	2020年4月20日	上午11:36:47
	 *  @Param  @return
	 */
	public Class<T> getClazz() {
		return clazz;
	}

	/**
	 * 获取所有属性
	 *	@ReturnType	Map<String,Field> 
	 *	@Date	2020年4月20日	上午11:36:52
	 *  @Param  @return
	 */
	public Map<String, Field> getFieldMap() {
		return fieldMap;
	}

	/**
	 * 获取所有属性
	 *	@ReturnType	List<Field> 
	 *	@Date	2020年4月20日	上午11:37:08
	 *  @Param  @return
	 */
	public List<Field> getFieldList() {
		return fieldList;
	}

	/**
	 * 获取所有属性
	 *	@ReturnType	Field[] 
	 *	@Date	2020年4月20日	上午11:37:11
	 *  @Param  @return
	 */
	public Field[] getFieldArray() {
		return fieldArray;
	}

	/**
	 * 获取所有方法
	 *	@ReturnType	List<Method> 
	 *	@Date	2020年4月20日	上午11:37:23
	 *  @Param  @return
	 */
	public List<Method> getMethodList() {
		return methodList;
	}

	/**
	 * 获取所有方法
	 *	@ReturnType	Method[] 
	 *	@Date	2020年4月20日	上午11:37:27
	 *  @Param  @return
	 */
	public Method[] getMethodArray() {
		return methodArray;
	}

	/**
	 * 根据属性名获取属性
	 *	@ReturnType	Field 
	 *	@Date	2020年4月20日	上午9:56:31
	 *  @Param  @param fieldName
	 *  @Param  @return
	 */
	public Field getField(String fieldName) {
		return fieldMap.get(fieldName);
	}
	
	/**
	 * 根据方法名获取无参方法
	 *	@ReturnType	Method 
	 *	@Date	2020年4月20日	上午9:56:43
	 *  @Param  @param methodName
	 *  @Param  @param parameterTypes
	 *  @Param  @return
	 */
	public Method getNoParamMethod(String methodName) {
		return noParamMethodMap.get(methodName);
	}
	
}
