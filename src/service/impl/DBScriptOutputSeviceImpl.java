package service.impl;

import java.util.List;
import java.util.Map;

import common.util.FileUtil;
import dao.DbDao;
import dao.impl.DbDaoImpl;
import service.DBScriptOutputSevice;

public class DBScriptOutputSeviceImpl implements DBScriptOutputSevice {
	private DbDao dbDao = new DbDaoImpl();

	@Override
	public void scriptAppend(String filepath,String str) {
		str = "\r\n" + str;
		FileUtil.wirte(filepath, str, true);
	}

	@Override
	public String getInsertSql(String user,String tableName,Object...objects) {
		StringBuilder sql = new StringBuilder();
		Map<String, String> tableValue = dbDao.getTableValue(user, tableName, objects);
		if(tableValue!= null) {
			sql.append("INSERT INTO " + tableName + " (");
			for (Map.Entry<String, String> entry : tableValue.entrySet()) {
				sql.append(entry.getKey() + ",");
			}
			sql.delete(sql.lastIndexOf(","), sql.lastIndexOf(",") + 1).append(") VALUES (");
			for (Map.Entry<String, String> entry : tableValue.entrySet()) {
				sql.append("'" + entry.getValue() + "',");
			}
			sql.delete(sql.lastIndexOf(","), sql.lastIndexOf(",") + 1).append(");");
		}
		return sql.toString();
	}

	@Override
	public String getUpdateSql(String user,String tableName,Object...objects) {
		StringBuilder sql = new StringBuilder();
		Map<String, String> tableValue = dbDao.getTableValue(user, tableName, objects);
		if(tableValue!= null) {
			sql.append("UPDATE " + tableName + " SET ");
			List<String> tableKey = dbDao.findTableKey(user,tableName);
			for (Map.Entry<String, String> entry : tableValue.entrySet()) {
				sql.append(entry.getKey() + "='" + entry.getValue() + "',");
			}
			sql.delete(sql.lastIndexOf(","), sql.lastIndexOf(",") + 1).append(" WHERE ");
			for (Map.Entry<String, String> entry : tableValue.entrySet()) {
				if(tableKey.contains(entry.getKey())) {
					sql.append(entry.getKey() + "='" + entry.getValue() + "' and ");
				}
			}
			sql.delete(sql.lastIndexOf("and"), sql.lastIndexOf("and") + 3).append(";");
		}
		return sql.toString();
	}

	@Override
	public String getDeleteSql(String user,String tableName,Object...objects) {
		StringBuilder sql = new StringBuilder();
		Map<String, String> tableValue = dbDao.getTableValue(user, tableName, objects);
		if(tableValue!= null) {
			sql.append("DELETE FROM " + tableName + " WHERE ");
			List<String> tableKey = dbDao.findTableKey(user,tableName);
			for (Map.Entry<String, String> entry : tableValue.entrySet()) {
				if(tableKey.contains(entry.getKey())) {
					sql.append(entry.getKey() + "='" + entry.getValue() + "' and ");
				}
			}
			sql.delete(sql.lastIndexOf("and"), sql.lastIndexOf("and") + 3).append(";");
		}
		return sql.toString();
	}

}
