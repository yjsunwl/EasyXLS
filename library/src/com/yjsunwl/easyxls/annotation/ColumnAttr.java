package com.yjsunwl.easyxls.annotation;

public class ColumnAttr {

	private String title;
	private String fieldName;

	public ColumnAttr() {

	}

	public ColumnAttr(String title, String fieldName) {
		this.title = title;
		this.fieldName = fieldName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
