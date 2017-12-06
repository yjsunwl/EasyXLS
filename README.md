EasyXLS
===========================
利用jxl-2.6.12.jar解析excel文件（因jxl只支持excel2003以下版本，所以目前只限于xls格式的文件）
****	
|Author|yjsunwl|
|---|---
|E-mail|yjsunwl@qq.com
## 功能
1. 读取文件数据序列化为JavaBean
2. 反序列化JavaBean写入文件
3. 创建、读取、写入、删除excel
## 使用方法
```java
  //创建并写入文件
  List<Employee> employees = new ArrayList<Employee>();
  employees.add(new Employee(1000, "Jones", 40, "Manager", 2975));
  employees.add(new Employee(1001, "Blake", 40, "Manager", 2850));
  employees.add(new Employee(1002, "Clark", 40, "Manager", 2450));
  employees.add(new Employee(1003, "Scott", 30, "Analyst", 3000));
  employees.add(new Employee(1004, "King", 50, "President", 5000));
  String[] titles = new String[] { "工号", "姓名", "年龄", "职称", "薪资" };
  String[] fieldNames = new String[] { "id", "name", "age", "job","salery" };

  try {
    XLSUtils xlsUtils = XLSUtils.getInstance(file);
    xlsUtils.writeExcel(Employee.class, employees, fieldNames, titles,"员工登记表");
  } catch (Exception e) {
    e.printStackTrace();
  }
  //读取文件
  String[] fieldNames = new String[] { "id", "name", "age", "job","salery" };
  List<Employee> employees = xlsUtils.readExcel(Employee.class,fieldNames, "员工登记表", true);
  //删除文件
  xlsUtils.deleteExcel(file);
```
## 使用EasyXLS需要有以下权限
```xml
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
