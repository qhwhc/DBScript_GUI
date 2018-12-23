package dao;

import java.util.List;

public interface ExcelDao {
//	从数据库读取表格
	public List<Object> readFromDB(String sql);
//	从Excel读取表格
	public List<Object> readFromExcel(String excelPath);
//	写入到数据库
	public String write2DB(String user,String tableName, List<Object> objects);
//	写入到表格
	public int write2Excel(String path,List<Object> objects);
}
