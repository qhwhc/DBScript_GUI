package service;

public interface ExcelService {
//	����sql�����ݿ����Excel
	public int db2ExcelBySql(String sql,String filePath);
	
//	����Excel�����ݿ�д�����ݿ�
	public String excel2Db(String user,String tableName,String filePath);
}
