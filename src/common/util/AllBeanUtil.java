package common.util;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

public class AllBeanUtil {
	public static Object generateObject(Map<Object, Class<?>> properties) {
        BeanGenerator generator = new BeanGenerator();
        Set<Object> keySet = properties.keySet();
        for(Iterator<Object> i = keySet.iterator(); i.hasNext();) {
            String key = (String)i.next();
            generator.addProperty(key, (Class<?>)properties.get(key));
        }
        return generator.create();
    }
	
	public static String getRealName(String proxyName) {
		return proxyName.substring("$cglib_prop_".length(),proxyName.length());
	}
	
	public static Object getValue(Object obj, String property) {
        BeanMap beanMap = BeanMap.create(obj);
        return beanMap.get(property);
    }

    public static void setValue(Object obj, String property, Object value) {
        BeanMap beanMap = BeanMap.create(obj);
        beanMap.put(property, value);
    }
    
    public static Map<String,String> bean2Map(Object object){
    	Map<String, String> map = new HashMap<String,String>();
    	Field[] fields = object.getClass().getDeclaredFields();
        for(Field field : fields) {
        	String key = getRealName(field.getName());
        	map.put(key, getValue(object,key) != null ? getValue(object,key).toString() : "");
        }
		return map;
    };
    
//    根据数据库表创建出映射关系
	public static Map<Object, Class<?>> createAllbeanProperties(String tableName) {
		DBConnUtil dBConnUtil = DBConnUtil.init();
		Map<Object, Class<?>> properties = new HashMap<Object, Class<?>>();
		String columnSql = "select t1.column_name,t2.comments,t1.data_length,t1.data_type,t1.data_scale from user_tab_columns t1 join user_col_comments t2 on t1.table_name = t2.table_name and t1.column_name = t2.column_name where t1.table_name = ?";
		try {
			ResultSet resultSet = DBConnUtil.executeQuery(dBConnUtil, columnSql,DBConnUtil.getParamList(tableName.toUpperCase()));
//			设置字段属性
			while(resultSet.next()) {
				if("VARCHAR2".equals(resultSet.getObject("data_type"))) {
					 properties.put(resultSet.getObject("column_name"), Class.forName("java.lang.String"));
				}else if("NUMBER".equals(resultSet.getObject("data_type"))){
					 properties.put(resultSet.getObject("column_name"), Class.forName("java.math.BigDecimal"));
				}else if("DATE".equals(resultSet.getObject("data_type"))) {
					properties.put(resultSet.getObject("column_name"), Class.forName("java.sql.Timestamp"));
				}else {
					 properties.put(resultSet.getObject("column_name"), Class.forName("java.lang.String"));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnUtil.close(dBConnUtil);
		}
		return properties;
	}
	
	public static Map<Object, Class<?>> createAllBeanBySql(String sql){
		DBConnUtil dBConnUtil = DBConnUtil.init();
		Map<Object, Class<?>> properties = new HashMap<Object, Class<?>>();
		try {
			ResultSet resultSet = DBConnUtil.executeQuery(dBConnUtil, sql,null);
//			设置字段属性
			int columns = resultSet.getMetaData().getColumnCount();
			ResultSetMetaData metaData = resultSet.getMetaData();
			for (int i = 0; i < columns; i++) {
				 properties.put(metaData.getColumnName(i + 1), Class.forName("java.lang.String"));
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnUtil.close(dBConnUtil);
		}
		return properties;
	};
	
	public static String allBeanToString(Object allbean) {
		StringBuilder str = new StringBuilder();
		Field[] fields = allbean.getClass().getDeclaredFields();
        for(Field field : fields) {
        	String key = getRealName(field.getName());
        	str.append(key + "=" + getValue(allbean,key) + "\t");
        }
		return str.length() > 0 ? str.delete(str.lastIndexOf("\t"), str.lastIndexOf("\t") + 2).toString() : "";
	}
}
