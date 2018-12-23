package service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.util.AllBeanUtil;
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
			sql.append("INSERT INTO " + tableName + "(");
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
			if(sql.lastIndexOf("and") > 0) {
				sql.delete(sql.lastIndexOf("and"), sql.lastIndexOf("and") + 3).append(";");
			}
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
			if(sql.lastIndexOf("and") > 0) {
				sql.delete(sql.lastIndexOf("and"), sql.lastIndexOf("and") + 3).append(";");
			}
		}
		return sql.toString();
	}

	@Override
	public List<String> getDeleteSqls(String user,String tableName,List<Object> objs) {
		List<String> sqls = new ArrayList<String>();
		List<String> keys = dbDao.findTableKey(user, tableName);
		if(objs.size() > 0) {
			Field[] fields = objs.get(0).getClass().getDeclaredFields();
			for (Object obj : objs) {
				StringBuilder sql = new StringBuilder();
				sql.append("DELETE FROM " + tableName + " WHERE ");
				for (Field field : fields) {
					String fieldName = AllBeanUtil.getRealName(field.getName());
					if(keys.contains(fieldName)) {
						sql.append(fieldName + "='" + AllBeanUtil.getValue(obj, fieldName) + "' and ");
					}
				}
				if(sql.lastIndexOf("and") > 0) {
					sql.delete(sql.lastIndexOf("and"), sql.lastIndexOf("and") + 3).append(";");
				}
				sqls.add(sql.toString());
			}
		}
		return sqls;
	}

	public List<String> getInsertSqls(String user,String tableName,List<Object> objs) {
		List<String> sqls = new ArrayList<String>();
		if(objs.size() > 0) {
			Field[] fields = objs.get(0).getClass().getDeclaredFields();
			for (Object obj : objs) {
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO " + tableName + "(");
				for (Field field : fields) {
					String fieldName = AllBeanUtil.getRealName(field.getName());
					sql.append(fieldName + ",");
				}
				sql.delete(sql.lastIndexOf(","), sql.lastIndexOf(",") + 1).append(") VALUES (");
				for (Field field : fields) {
					String fieldName = AllBeanUtil.getRealName(field.getName());
					sql.append("'" + AllBeanUtil.getValue(obj, fieldName) + "',");
				}
				sql.delete(sql.lastIndexOf(","), sql.lastIndexOf(",") + 1).append(");");
				sqls.add(sql.toString());
			}
		}
		return sqls;
	}
}
