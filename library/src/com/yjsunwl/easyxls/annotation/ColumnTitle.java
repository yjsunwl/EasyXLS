package com.yjsunwl.easyxls.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnTitle {
	String columnTitle() default "";

	int columnIndex() default Integer.MAX_VALUE;
}
