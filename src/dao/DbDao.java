package dao;

import java.util.List;
import java.util.Map;

import dto.DbTable;

public interface DbDao {
//	获取数据库表格属性
	public DbTable get(String user,String tableName);
//	将每一条数据放入object
	public List<Object> findByTable(String user,String tableName,Object...objects);
//	获取主键名称
	public List<String> findTableKey(String user,String tableName);
//	生成映射关系
	public Map<Object, Class<?>> createAllbeanProperties(String tableName); 
//	获取bean值
	public Map<String,String> getTableValue(String user,String tableName,Object...objects);
	
}
