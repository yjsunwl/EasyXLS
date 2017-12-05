package com.yjsunwl.sample.easyxls;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yjsunwl.easyxls.XLSUtils;

public class MainActivity extends Activity {

	private Context mContext;
	private XLSUtils xlsUtils;
	private File mFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();

	}

	private void initData() {
		String filePath = Environment.getExternalStorageDirectory()
				+ "/Sample_EasyXLS.xls";
		mFile = new File(filePath);
		xlsUtils = XLSUtils.getInstance(mFile);
	}

	private void initView() {
		mContext = MainActivity.this;
		String[] examples = new String[] { "创建及写入excel", "删除excel", "读取excel" };
		ListView listView = (ListView) findViewById(R.id.lv_main);
		IndexAdapter adapter = new IndexAdapter(this, Arrays.asList(examples));
		listView.setAdapter(adapter);
		setTitle("EasyXLS");
		listView.setOnItemClickListener(mItemClickListener);
	}

	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (position == 0) {
				try {
					writeExcel(mFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (position == 1) {
				if (xlsUtils.deleteExcel(mFile)) {
					Toast.makeText(mContext, "excel删除成功", Toast.LENGTH_SHORT)
							.show();
				}
			} else if (position == 2) {
				readExcel(mFile);
			}

		}
	};

	protected void writeExcel(File file) {
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(new Employee(1000, "Jones", 40, "Manager", 2975));
		employees.add(new Employee(1001, "Blake", 40, "Manager", 2850));
		employees.add(new Employee(1002, "Clark", 40, "Manager", 2450));
		employees.add(new Employee(1003, "Scott", 30, "Analyst", 3000));
		employees.add(new Employee(1004, "King", 50, "President", 5000));
		String[] titles = new String[] { "工号", "姓名", "年龄", "职称", "薪资" };
		String[] fieldNames = new String[] { "id", "name", "age", "job",
				"salery" };

		try {
			XLSUtils xlsUtils = XLSUtils.getInstance(file);
			xlsUtils.writeExcel(Employee.class, employees, fieldNames, titles,
					"员工登记表");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void readExcel(File file) {
		try {
			String[] fieldNames = new String[] { "id", "name", "age", "job",
					"salery" };
			List<Employee> employees = xlsUtils.readExcel(Employee.class,
					fieldNames, "员工登记表", true);
			if (employees == null) {
				return;
			}
			for (Employee employee : employees) {
				System.out.println(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
