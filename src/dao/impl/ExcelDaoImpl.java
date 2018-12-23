package dao.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.util.AllBeanUtil;
import common.util.DBConnUtil;
import common.util.ExcelUtil;
import dao.DbDao;
import dao.ExcelDao;

public class ExcelDaoImpl implements ExcelDao {
	private DbDao dbDao= new DbDaoImpl(); 
	@Override
	public List<Object> readFromDB(String sql) {
		DBConnUtil dBConnUtil = DBConnUtil.init();
		List<Object> objs = new ArrayList<Object>();
		try {
			ResultSet resultSet = DBConnUtil.executeQuery(dBConnUtil,sql,null);
			Map<Object, Class<?>> properties = AllBeanUtil.createAllBeanBySql(sql);
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
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnUtil.close(dBConnUtil);
		}
		return objs;
	}

	@Override
	public List<Object> readFromExcel(String excelPath) {
		return ExcelUtil.readExcel(excelPath);
	}

	@Override
	public String write2DB(String user,String tableName, List<Object> objects) {
		DBConnUtil dBConnUtil = DBConnUtil.init();
		int executeUpdate = 0;
		int executeinsert = 0;
		Field[] fields = null;
		try {
			if(objects.size() > 0) {
				for (Object object : objects) {
					StringBuilder sql = new StringBuilder();
					sql.append("INSERT INTO " + tableName + " (");
					fields = object.getClass().getDeclaredFields();
			        for(Field field : fields) {
			        	String key = AllBeanUtil.getRealName(field.getName());
			        	sql.append(key + ",");
			        }
					sql.delete(sql.lastIndexOf(","), sql.lastIndexOf(",") + 1).append(") VALUES (");
					for(Field field : fields) {
			        	String key = AllBeanUtil.getRealName(field.getName());
			        	sql.append("'" + AllBeanUtil.getValue(object, key) + "',");
			        }
					sql.delete(sql.lastIndexOf(","), sql.lastIndexOf(",") + 1).append(")");
//					执行插入sql语句
					try {
						executeinsert += DBConnUtil.executeUpdate(dBConnUtil, sql.toString(), null);
					} catch (SQLIntegrityConstraintViolationException e) {
						//如果存在唯一约束异常，则进行更新sql
						StringBuilder updatesql = new StringBuilder();
						List<String> keyNames = dbDao.findTableKey(user,tableName);
						if(keyNames .size() > 0) {
							updatesql.append("UPDATE " + tableName + " SET ");
							 for(Field field : fields) {
						        	String key = AllBeanUtil.getRealName(field.getName());
						        	updatesql.append(key + "='" + AllBeanUtil.getValue(object, key) + "',");
						        }
							 updatesql.delete(updatesql.lastIndexOf(","), updatesql.lastIndexOf(",") + 1).append(" WHERE ");
							for(Field field : fields) {
								String key = AllBeanUtil.getRealName(field.getName());
								if(keyNames .contains(key)) {
						        	updatesql.append(key + "='" + AllBeanUtil.getValue(object, key) + "' and ");
								}
					        }
							updatesql.delete(updatesql.lastIndexOf("and"), updatesql.lastIndexOf("and") + 3);
							if(updatesql.indexOf("WHERE") > 0) {
								DBConnUtil.executeUpdate(dBConnUtil, updatesql.toString(), null);
							}
						}
						executeUpdate += 1;
					} 
				}
			}	
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnUtil.close(dBConnUtil);
		}
		return String.valueOf(executeinsert) + "," + String.valueOf(executeUpdate);
	}

	@Override
	public int write2Excel(String path, List<Object> objects) {
		ExcelUtil.writeExcel(path, objects);
		return objects.size();
	}

}
