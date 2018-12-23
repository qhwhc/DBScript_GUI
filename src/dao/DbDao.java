package dao;

import java.util.List;
import java.util.Map;

import dto.DbTable;

public interface DbDao {
//	��ȡ���ݿ�������
	public DbTable get(String user,String tableName);
//	��ÿһ�����ݷ���object
	public List<Object> findByTable(String user,String tableName,Object...objects);
//	��ȡ��������
	public List<String> findTableKey(String user,String tableName);
//	����ӳ���ϵ
	public Map<Object, Class<?>> createAllbeanProperties(String tableName); 
//	��ȡbeanֵ
	public Map<String,String> getTableValue(String user,String tableName,Object...objects);
	
}
