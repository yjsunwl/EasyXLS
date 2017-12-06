package com.yjsunwl.sample.easyxls;

import com.yjsunwl.easyxls.annotation.ColumnTitle;

public class Employee {
	@ColumnTitle(columnTitle = "工号", columnIndex = 0)
	private long id;

	@ColumnTitle(columnTitle = "姓名", columnIndex = 1)
	private String name;

	@ColumnTitle(columnTitle = "年龄", columnIndex = 2)
	private int age;

	@ColumnTitle(columnTitle = "职位", columnIndex = 3)
	private String job;

	@ColumnTitle(columnTitle = "薪资", columnIndex = 4)
	private double salery;

	public Employee() {

	}

	public Employee(long id, String name, int age, String job, double salery) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.job = job;
		this.salery = salery;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public double getSalery() {
		return salery;
	}

	public void setSalery(double salery) {
		this.salery = salery;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age
				+ ", job=" + job + ", salery=" + salery + "]";
	}
}
