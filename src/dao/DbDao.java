package dao;

import java.util.List;
import java.util.Map;

import dto.DbTable;

public interface DbDao {
	public DbTable get(String user,String tableName);
	
	public List<Object> findByTable(String user,String tableName,Object...objects);
	
	public List<String> findTableKey(String user,String tableName);
	
	public Map<Object, Class<?>> createAllbeanProperties(String tableName); 
	
	public Map<String,String> getTableValue(String user,String tableName,Object...objects);
}
