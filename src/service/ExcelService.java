package service;

public interface ExcelService {
//	根据sql从数据库读到Excel
	public int db2ExcelBySql(String sql,String filePath);
	
//	根据Excel把数据库写入数据库
	public String excel2Db(String user,String tableName,String filePath);
}
