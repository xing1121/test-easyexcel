package com.wdx.easy.domain;

/**
 * 描述：Cat
 * @author 80002888
 * @date   2018年9月11日
 */
public class Cat {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 猫的名字
	 */
	private String name;

	/**
	 * 重量
	 */
	private Float weight;

	/**
	 * 性别
	 */
	private Character sex;

	public Cat() {
	}

	public Cat(Long id, String name, Float weight, Character sex) {
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.sex = sex;
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

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Character getSex() {
		return sex;
	}

	public void setSex(Character sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "Cat [id=" + id + ", name=" + name + ", weight=" + weight + ", sex=" + sex + "]";
	}

}
