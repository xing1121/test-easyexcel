package com.wdx.easy.domain;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 描述：BasePerson
 * @author 80002888
 * @date   2018年9月10日
 */
public abstract class BasePerson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6330289063503034622L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 生日日期
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date birthDay;

	/**
	 * 生日
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date birthDayTime;

	/**
	 * 是否有工作
	 */
	private Boolean workStatus;

	/**
	 * 薪水
	 */
	private Double salary;

	public BasePerson() {
	}

	public BasePerson(Long id, String name, Integer age, Date birthDay, Date birthDayTime, Boolean workStatus,
			Double salary) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.birthDay = birthDay;
		this.birthDayTime = birthDayTime;
		this.workStatus = workStatus;
		this.salary = salary;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Date getBirthDayTime() {
		return birthDayTime;
	}

	public void setBirthDayTime(Date birthDayTime) {
		this.birthDayTime = birthDayTime;
	}

	public Boolean getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(Boolean workStatus) {
		this.workStatus = workStatus;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
