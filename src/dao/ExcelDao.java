package dao;

import java.util.List;

public interface ExcelDao {
//	�����ݿ��ȡ���
	public List<Object> readFromDB(String sql);
//	��Excel��ȡ���
	public List<Object> readFromExcel(String excelPath);
//	д�뵽���ݿ�
	public String write2DB(String user,String tableName, List<Object> objects);
//	д�뵽���
	public int write2Excel(String path,List<Object> objects);
}
