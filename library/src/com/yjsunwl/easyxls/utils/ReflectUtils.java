package com.yjsunwl.easyxls.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {

	/**
	 * 反射调用指定构造方法创建对象
	 * 
	 * @param clazz 对象类型
	 * @param argTypes 参数类型
	 * @param args 构造参数
	 * @return 返回构造后的对象
	 * 
	 */
	public static <T> T invokeConstructor(Class<T> clazz, Class<?>[] argTypes,
			Object[] args) throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Constructor<T> constructor = clazz.getConstructor(argTypes);
		return constructor.newInstance(args);
	}

	/**
	 * 反射调用指定对象属性的getter方法
	 * 
	 * @param <T> 泛型
	 * @param target 指定对象
	 * @param fieldName 属性名
	 * @return 返回调用后的值
	 * 
	 */
	public static <T> Object invokeGetter(T target, String fieldName)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		// 如果属性名为xxx，则方法名为getXxx
		String methodName = "get" + StringUtils.firstCharUpperCase(fieldName);
		Method method = target.getClass().getMethod(methodName);
		return method.invoke(target);
	}

	/**
	 * 反射调用指定对象属性的setter方法
	 * 
	 * @param <T> 泛型
	 * @param target 指定对象
	 * @param fieldName 属性名
	 * @param argTypes 参数类型
	 * @param args 参数列表
	 * 
	 */
	public static <T> void invokeSetter(T target, String fieldName, Object args)
			throws NoSuchFieldException, SecurityException,
			NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// 如果属性名为xxx，则方法名为setXxx
		String methodName = "set" + StringUtils.firstCharUpperCase(fieldName);
		Class<?> clazz = target.getClass();
		Field field = clazz.getDeclaredField(fieldName);
		Method method = clazz.getMethod(methodName, field.getType());
		method.invoke(target, args);
	}

	/**
	 * 根据类型将指定参数转换成对应的类型
	 * @param value 指定参数
	 * @param type 指定类型
	 * @return 返回类型转换后的对象
	 */
	public static <T> Object parseValueWithType(String value, Class<?> type) {
		Object result = null;
		try { // 根据属性的类型将内容转换成对应的类型
			if (Boolean.TYPE == type) {
				result = Boolean.parseBoolean(value);
			} else if (Byte.TYPE == type) {
				result = Byte.parseByte(value);
			} else if (Short.TYPE == type) {
				result = Short.parseShort(value);
			} else if (Integer.TYPE == type) {
				result = Integer.parseInt(value);
			} else if (Long.TYPE == type) {
				result = Long.parseLong(value);
			} else if (Float.TYPE == type) {
				result = Float.parseFloat(value);
			} else if (Double.TYPE == type) {
				result = Double.parseDouble(value);
			} else {
				result = (Object) value;
			}
		} catch (Exception e) {
			
		}
		return result;
	}	
}
