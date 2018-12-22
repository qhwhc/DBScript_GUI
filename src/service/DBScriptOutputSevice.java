package service;


public interface DBScriptOutputSevice {
	public void scriptAppend(String filepath,String str);
	
	public String getInsertSql(String user,String tableName,Object...objects);
	
	public String getUpdateSql(String user,String tableName,Object...objects);
	
	public String getDeleteSql(String user,String tableName,Object...objects);
}
