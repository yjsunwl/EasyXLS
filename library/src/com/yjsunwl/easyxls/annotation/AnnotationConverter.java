package com.yjsunwl.easyxls.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationConverter {
	public static ColumnAttr[] getColumnAttr(Field[] fields) {
		HashMap<ColumnAttr, Integer> map = new HashMap<ColumnAttr, Integer>();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				ColumnTitle columnTitle = field
						.getAnnotation(ColumnTitle.class);
				if (columnTitle != null) {
					map.put(new ColumnAttr(columnTitle.columnTitle(), field
							.getName()), columnTitle.columnIndex());
				}
			}
		}
		List<Map.Entry<ColumnAttr, Integer>> list = new ArrayList<Map.Entry<ColumnAttr, Integer>>(
				map.entrySet());
		Collections.sort(list,
				new Comparator<Map.Entry<ColumnAttr, Integer>>() {

					@Override
					public int compare(Map.Entry<ColumnAttr, Integer> o1,
							Map.Entry<ColumnAttr, Integer> o2) {
						return o1.getValue().compareTo(o2.getValue());
					}
				});
		ColumnAttr[] res = new ColumnAttr[list.size()];
		for (int i = 0; i < list.size(); i++) {
			res[i] = list.get(i).getKey();
		}
		return res;
	}
}
