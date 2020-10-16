package com.wdx.easy.dto;

import java.util.List;

/**
 * 
 * 描述：EasyExcel参数对象



 * @author wdx
 * @date   2020年10月16日
 */
public class EasyExcelParamDto {
	
	/**
	 * 表名
	 */
	private String sheetName;
	
	/**
	 * xlsx文件中第一行（表头）按顺序显示的列名，可以为null
	 */
	private List<String> headers;
	
	/**
	 * 数据集合
	 */
	private List<?> sourceList;
	
	/**
	 * 属性名称
	 */
	private List<String> propertyNames;
	
	/**
	 * 属性保留还是去除
	 */
	private boolean keepFlag;
	
	public EasyExcelParamDto() {
	}
	
	public EasyExcelParamDto(
			String sheetName, 
			List<String> headers, 
			List<?> sourceList, 
			List<String> propertyNames,
			boolean keepFlag) {
		super();
		this.sheetName = sheetName;
		this.headers = headers;
		this.sourceList = sourceList;
		this.propertyNames = propertyNames;
		this.keepFlag = keepFlag;
	}
	
	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<?> getSourceList() {
		return sourceList;
	}

	public void setSourceList(List<?> sourceList) {
		this.sourceList = sourceList;
	}

	public List<String> getPropertyNames() {
		return propertyNames;
	}

	public void setPropertyNames(List<String> propertyNames) {
		this.propertyNames = propertyNames;
	}

	public boolean isKeepFlag() {
		return keepFlag;
	}

	public void setKeepFlag(boolean keepFlag) {
		this.keepFlag = keepFlag;
	}
	
}
