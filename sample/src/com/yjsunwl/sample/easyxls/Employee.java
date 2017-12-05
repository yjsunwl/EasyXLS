package com.yjsunwl.sample.easyxls;

public class Employee {
	private long id;
	private String name;
	private int age;
	private String job;
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
