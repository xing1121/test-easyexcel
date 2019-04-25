package com.wdx.easy.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：Person
 * @author 80002888
 * @date   2018年9月10日
 */
public class Person implements Serializable {

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
	private Date birthDay;

	/**
	 * 生日日期及时间
	 */
	private Date birthDayTime;

	/**
	 * 是否有工作
	 */
	private Boolean workStatus;

	/**
	 * 薪水
	 */
	private Double salary;

	public Person() {
	}

	public Person(Long id, String name, Integer age, Date birthDay, Date birthDayTime, Boolean workStatus,
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
		return "Person [id=" + id + ", name=" + name + ", age=" + age + ", birthDay=" + birthDay + ", birthDayTime="
				+ birthDayTime + ", workStatus=" + workStatus + ", salary=" + salary + "]";
	}

}
