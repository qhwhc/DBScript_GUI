package dao.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.util.AllBeanUtil;
import common.util.DBConnUtil;
import dao.DbDao;
import dto.DbColumn;
import dto.DbTable;

public class DbDaoImpl implements DbDao {
	@Override
	public DbTable get(String user, String tableName) {
		DBConnUtil dBConnUtil = DBConnUtil.init();
		DbTable dbTable = new DbTable();
		String commentSql = "select comments from user_tab_comments where table_name = ?";
		String primaryKeySql = "select column_name from user_cons_columns where constraint_name = (select constraint_name from dba_constraints where constraint_type = 'P' and table_name = ? and owner = ?)";
		String columnSql = "select t1.column_name,t2.comments,t1.data_length,t1.data_type,t1.data_scale from user_tab_columns t1 join user_col_comments t2 on t1.table_name = t2.table_name and t1.column_name = t2.column_name where t1.table_name = ?";
		try {
			ResultSet resultSet = DBConnUtil.executeQuery(dBConnUtil, commentSql,DBConnUtil.getParamList(tableName.toUpperCase()));
//			设置表备注
			if(resultSet.next()) dbTable.setComment(resultSet.getString("comments"));
			resultSet = DBConnUtil.executeQuery(dBConnUtil, primaryKeySql,DBConnUtil.getParamList(tableName.toUpperCase(),user.toUpperCase()));
//			设置主键名
			if(resultSet.next()) dbTable.setPrimaryKey(resultSet.getString("column_name"));
			resultSet = DBConnUtil.executeQuery(dBConnUtil, columnSql,DBConnUtil.getParamList(tableName.toUpperCase()));
//			设置字段属性
			List<DbColumn> columns = new ArrayList<DbColumn>();
			while(resultSet.next()) {
				DbColumn column = new DbColumn();
				column.setColumnName(resultSet.getString("column_name"));
				column.setComments(resultSet.getString("comments"));
				column.setDataLength(resultSet.getString("data_length"));
				column.setDataScale(resultSet.getString("data_scale"));
				column.setDataType(resultSet.getString("data_type"));
				columns.add(column);
			}
			dbTable.setColumns(columns);
			dbTable.setTableName(tableName.toUpperCase());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnUtil.close(dBConnUtil);
		}
		return dbTable;
	}
	@Override
	public List<Object> findByTable(String user,String tableName,Object...objects) {
		DBConnUtil dBConnUtil = DBConnUtil.init();
		List<Object> objs = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder("select * from " + tableName + " where " );
		List<String> keys = findTableKey(user,tableName);
		for (int i = 0,lenth = keys.size(); i < lenth; i++) {
			sql.append(keys.get(i)).append("=").append("?").append(" and ");
		}
		if(sql.toString().contains("and")) {
			sql.delete(sql.lastIndexOf("and"), sql.lastIndexOf("and") + 3);
		}
		try {
			ResultSet resultSet = DBConnUtil.executeQuery(dBConnUtil, sql.toString(),DBConnUtil.getParamList(objects));
			Map<Object, Class<?>> properties = createAllbeanProperties(tableName);
			Field[] fields = null;
			while (resultSet.next()) {
				Object object = AllBeanUtil.generateObject(properties);
				fields = object.getClass().getDeclaredFields();
		        for(Field field : fields) {
		        	String key = AllBeanUtil.getRealName(field.getName());
		        	AllBeanUtil.setValue(object, key, resultSet.getObject(key));
		        }
		        objs.add(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnUtil.close(dBConnUtil);
		}
		return objs;
	}
	@Override
	public List<String> findTableKey(String user, String tableName) {
		DBConnUtil dBConnUtil = DBConnUtil.init();
		List<String> primaryKeys = new ArrayList<String>();
		String primaryKeySql = "select column_name from user_cons_columns where constraint_name = (select constraint_name from dba_constraints where constraint_type = 'P' and table_name = ? and owner = ?)";
		try {
			ResultSet resultSet = DBConnUtil.executeQuery(dBConnUtil, primaryKeySql,DBConnUtil.getParamList(tableName.toUpperCase(),user.toUpperCase()));
			while(resultSet.next()) {
				primaryKeys.add(resultSet.getString("COLUMN_NAME"));
			};
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnUtil.close(dBConnUtil);
		}
		return primaryKeys;
	}
	@Override
	public Map<Object, Class<?>> createAllbeanProperties(String tableName) {
		DBConnUtil dBConnUtil = DBConnUtil.init();
		Map<Object, Class<?>> properties = new HashMap<Object, Class<?>>();
		String columnSql = "select t1.column_name,t2.comments,t1.data_length,t1.data_type,t1.data_scale from user_tab_columns t1 join user_col_comments t2 on t1.table_name = t2.table_name and t1.column_name = t2.column_name where t1.table_name = ?";
		try {
			ResultSet resultSet = DBConnUtil.executeQuery(dBConnUtil, columnSql,DBConnUtil.getParamList(tableName.toUpperCase()));
//			设置字段属性
			while(resultSet.next()) {
				Object res = resultSet.getObject("data_type");
				if("VARCHAR2".equals(res)) {
					 properties.put(resultSet.getObject("column_name"), Class.forName("java.lang.String"));
				}else if("NUMBER".equals(res)){
					 properties.put(resultSet.getObject("column_name"), Class.forName("java.math.BigDecimal"));
				}else if("DATE".equals(res)) {
					 properties.put(resultSet.getObject("column_name"), Class.forName("java.sql.Timestamp"));
				}else {
					 properties.put(resultSet.getObject("column_name"), Class.forName("java.lang.String"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnUtil.close(dBConnUtil);
		}
		return properties;
	}
	@Override
	public Map<String, String> getTableValue(String user, String tableName, Object... objects) {
		List<Object> list = findByTable(user, tableName,objects);
		return list.size() > 0 ?AllBeanUtil.bean2Map(list.get(0)) : null;
	}
}
