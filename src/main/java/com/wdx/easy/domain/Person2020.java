package com.wdx.easy.domain;

import java.util.Date;

import com.alibaba.fastjson.JSON;

/**
 * 描述：Person2020
 * @author 80002888
 * @date   2020年4月17日
 */
public class Person2020 extends BasePerson {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3125708246678270514L;

	/**
	 * 工作名称
	 */
	private String jobName;
	
	/**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 入职日期
	 */
	private Date jobDate;

	public Person2020() {
	}

	public Person2020(Long id, 
			String name, 
			Integer age, 
			Date birthDay,
			Date birthDayTime, 
			Boolean workStatus,
			Double salary,
			String jobName, 
			String companyName,
			Date jobDate) {
		super(id, name, age, birthDay, birthDayTime, workStatus, salary);
		this.jobName = jobName;
		this.companyName = companyName;
		this.jobDate = jobDate;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getJobDate() {
		return jobDate;
	}

	public void setJobDate(Date jobDate) {
		this.jobDate = jobDate;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
