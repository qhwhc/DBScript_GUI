package service;

import java.util.List;

public interface DBScriptOutputSevice {
	public void scriptAppend(String filepath,String str);
	
	public String getInsertSql(String user,String tableName,Object...objects);
	
	public String getUpdateSql(String user,String tableName,Object...objects);
	
	public String getDeleteSql(String user,String tableName,Object...objects);
	
	public List<String> getDeleteSqls(String user,String tableName,List<Object> objs);
	
	public List<String> getInsertSqls(String user,String tableName,List<Object> objs);
}
