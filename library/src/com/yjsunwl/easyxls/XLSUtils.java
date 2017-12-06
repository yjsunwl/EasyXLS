package com.yjsunwl.easyxls;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.yjsunwl.easyxls.annotation.AnnotationConverter;
import com.yjsunwl.easyxls.annotation.ColumnAttr;
import com.yjsunwl.easyxls.utils.ReflectUtils;
import com.yjsunwl.easyxls.utils.StringUtils;

public class XLSUtils {

	/**
	 * 对象序列化版本号名称
	 */
	public static final String UID = "serialVersionUID";

	private static XLSUtils instance = null;
	private File file; // 操作文件

	public XLSUtils(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * 获取单例对象并进行初始化
	 * @param file 文件对象
	 * @return 返回初始化后的单例对象
	 */
	public synchronized static XLSUtils getInstance(File file) {
		if (instance == null) {
			instance = new XLSUtils(file);
		} else {
			// 如果操作的文件对象不同，则重置文件对象
			if (!file.equals(instance.getFile())) {
				instance.setFile(file);
			}
		}
		return instance;
	}

	/**
	 * 获取单例对象并进行初始化
	 * @param filePath 文件路径
	 * @return 返回初始化后的单例对象
	 */
	public static XLSUtils getInstance(String filePath) {
		return getInstance(new File(filePath));
	}

	/**
	 * 删除excel文件
	 * @param file
	 * @return 结果
	 */
	public boolean deleteExcel(File file) {
		if (file.isFile() && file.exists()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * 创建并写入excel文件，如果文件存在，则直接写入,title使用javabean的属性名
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @param sheetName sheet名字
	 */
	public <T> void writeExcel(Class<T> clazz, List<T> dataModels,
			String sheetName) throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			fieldNames[i] = fieldName;
		}
		writeExcel(clazz, dataModels, fieldNames, fieldNames, sheetName);
	}

	/**
	 * 创建并写入excel文件，如果文件存在，则直接写入,title使用javabean的属性名
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @param fieldNames 属性列表
	 * @param sheetName sheet名字
	 */
	public <T> void writeExcel(Class<T> clazz, List<T> dataModels,
			String[] fieldNames, String sheetName) throws Exception {
		writeExcel(clazz, dataModels, fieldNames, fieldNames, sheetName);
	}

	/**
	 * 创建并写入excel文件，如果文件存在，则直接写入
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @param fieldNames 属性列表
	 * @param titles sheet标题列表
	 * @param sheetName sheet名字
	 */
	public <T> void writeExcel(Class<T> clazz, List<T> dataModels,
			String[] fieldNames, String[] titles, String sheetName)
			throws Exception {
		WritableWorkbook workbook = null;
		try {
			// 检测文件是否存在，如果存在则修改文件，否则创建文件		
			if (file.exists()) {
				Workbook book = Workbook.getWorkbook(file);
				workbook = Workbook.createWorkbook(file, book);
			} else {
				workbook = Workbook.createWorkbook(file);
			}
			// 判断sheet是否存在，如果存在则在修改，否则创建sheet
			// 根据当前工作表数量创建相应编号的工作表
			int sheetNo = workbook.getNumberOfSheets() + 1;
			WritableSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				sheet = workbook.createSheet(sheetName, sheetNo);
			}
			// 添加表格标题
			for (int i = 0; i < titles.length; i++) {
				// 设置字体加粗
				WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
						WritableFont.BOLD);
				WritableCellFormat format = new WritableCellFormat(font);
				// 设置自动换行
				format.setWrap(true);
				Label label = new Label(i, 0, titles[i], format);
				sheet.addCell(label);
				// 设置单元格宽度
				sheet.setColumnView(i, titles[i].length() + 10);
			}
			// 添加表格内容
			int rowNum = sheet.getRows();
			for (int i = 0; i < dataModels.size(); i++) {
				// 遍历属性列表
				for (int j = 0; j < fieldNames.length; j++) {
					T target = dataModels.get(i);
					// 通过反射获取属性的值域
					String fieldName = fieldNames[j];
					if (fieldName == null || UID.equals(fieldName)) {
						continue; // 过滤serialVersionUID属性
					}
					Object result = ReflectUtils
							.invokeGetter(target, fieldName);
					Label label = new Label(j, i + rowNum,
							StringUtils.toString(result));
					sheet.addCell(label);
				}
			}
		} finally {
			if (workbook != null) {
				workbook.write();
				workbook.close();
			}
		}
	}

	/**
	 * 通过注解的方式写excel
	 * @param clazz 数据类型
	 * @param dataModels 数据列表
	 * @param sheetName 
	 */
	public <T> void writeExcelByAnnotation(Class<T> clazz, List<T> dataModels,
			String sheetName) throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		ColumnAttr[] columnAttrs = AnnotationConverter.getColumnAttr(fields);
		String[] titles = new String[columnAttrs.length];
		String[] fieldNames = new String[columnAttrs.length];
		for (int i = 0; i < columnAttrs.length; i++) {
			titles[i] = columnAttrs[i].getTitle();
			fieldNames[i] = columnAttrs[i].getFieldName();
		}
		writeExcel(clazz, dataModels, fieldNames, titles, sheetName);
	}

	/**
	 * 根据sheet名称 找到sheetNo
	 * @param sheetName
	 * @return sheetNo，如果没有对应名称返回-1
	 * @throws Exception
	 */
	private int findSheetNoFromName(String sheetName) throws Exception {
		Workbook workbook = Workbook.getWorkbook(file);
		int i = 0;
		try {
			String[] sheetNames = workbook.getSheetNames();
			for (i = 0; i < sheetNames.length; i++) {
				if (sheetNames[i].equals(sheetName)) {
					break;
				}
				if (i == sheetNames.length - 1) {
					return -1;
				}
			}
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return i;
	}

	/**
	 * 读取excel文件
	 * @param clazz 数据类型
	 * @param fieldNames 属性列表
	 * @param sheetName  sheet名称
	 * @param hasTitle 第一行是否存在标题
	 * @return javabean数据
	 */
	public <T> List<T> readExcel(Class<T> clazz, String[] fieldNames,
			String sheetName, boolean hasTitle) throws Exception {
		int index = findSheetNoFromName(sheetName);
		if (index == -1) {
			return null;
		}
		return readExcel(clazz, fieldNames, index, hasTitle);
	}

	/**
	 * 读取excel文件
	 * @param clazz 数据类型
	 * @param fieldNames 属性列表
	 * @param sheetNo  sheet序号（从0开始）
	 * @param hasTitle 第一行是否存在标题
	 * @return javabean数据
	 */
	public <T> List<T> readExcel(Class<T> clazz, String[] fieldNames,
			int sheetNo, boolean hasTitle) throws Exception {
		List<T> dataModels = new ArrayList<T>();
		// 获取excel工作簿
		Workbook workbook = Workbook.getWorkbook(file);
		try {
			Sheet sheet = workbook.getSheet(sheetNo);
			int start = hasTitle ? 1 : 0; // 如果有标题则从第二行开始
			for (int i = start; i < sheet.getRows(); i++) {
				// 生成实例并通过反射调用setter方法
				T target = clazz.newInstance();
				for (int j = 0; j < fieldNames.length; j++) {
					String fieldName = fieldNames[j];
					if (fieldName == null || UID.equals(fieldName)) {
						continue; // 过滤serialVersionUID属性
					}
					// 获取excel单元格的内容
					Cell cell = sheet.getCell(j, i);
					if (cell == null) {
						continue;
					}
					String content = cell.getContents();
					Field field = clazz.getDeclaredField(fieldName);
					ReflectUtils.invokeSetter(
							target,
							fieldName,
							ReflectUtils.parseValueWithType(content,
									field.getType()));
				}
				dataModels.add(target);
			}
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return dataModels;
	}

	/**
	 * 注解方式读取excel文件
	 * @param clazz 数据类型
	 * @param sheetNo  sheet序号（从0开始）
	 * @param hasTitle 第一行是否存在标题
	 * @return javabean数据
	 */
	public <T> List<T> readExcelByAnnotation(Class<T> clazz, int sheetNo,
			boolean hasTitle) throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		ColumnAttr[] columnAttrs = AnnotationConverter.getColumnAttr(fields);
		String[] titles = new String[columnAttrs.length];
		String[] fieldNames = new String[columnAttrs.length];
		for (int i = 0; i < columnAttrs.length; i++) {
			titles[i] = columnAttrs[i].getTitle();
			fieldNames[i] = columnAttrs[i].getFieldName();
		}
		return readExcel(clazz, fieldNames, sheetNo, hasTitle);
	}

	/**
	 * 注解方式读取excel文件
	 * @param clazz 数据类型
	 * @param sheetName  sheet名称
	 * @param hasTitle 第一行是否存在标题
	 * @return javabean数据
	 */
	public <T> List<T> readExcelByAnnotation(Class<T> clazz, String sheetName,
			boolean hasTitle) throws Exception {
		Field[] fields = clazz.getDeclaredFields();
		ColumnAttr[] columnAttrs = AnnotationConverter.getColumnAttr(fields);
		String[] titles = new String[columnAttrs.length];
		String[] fieldNames = new String[columnAttrs.length];
		for (int i = 0; i < columnAttrs.length; i++) {
			titles[i] = columnAttrs[i].getTitle();
			fieldNames[i] = columnAttrs[i].getFieldName();
		}
		return readExcel(clazz, fieldNames, sheetName, hasTitle);
	}
}
