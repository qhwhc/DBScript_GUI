package test;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import common.util.AllBeanUtil;
import common.util.DBConnUtil;
import dao.impl.DbDaoImpl;
import service.impl.DBScriptOutputSeviceImpl;

public class DBTest {

	@Test
	public void testConnect() {
		try {
			DBConnUtil dbConnUtil = DBConnUtil.init();
			ResultSet result = DBConnUtil.executeQuery(dbConnUtil, "select * from testt1 ", null);
			while(result.next()) {
				System.out.println(result.getString("name"));
			}
			result.close();
			DBConnUtil.close(dbConnUtil);
			List<Object> list = new DbDaoImpl().findByTable("SYSTEM", "TESTT1","1","张三");
			for (Object object : list) {
				System.out.println(AllBeanUtil.bean2Map(object));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void allBeanTest() {
		Map<Object, Class<?>> properties = new DbDaoImpl().createAllbeanProperties("testt1");
		Object stu = AllBeanUtil.generateObject(properties);
		Field[] fields = stu.getClass().getDeclaredFields();
	        for(Field field : fields) {
	            System.out.println(AllBeanUtil.getRealName(field.getName()));
	    }
	}
	
	@Test
	public void testGetMap() {
		System.out.println(new DbDaoImpl().getTableValue("SYSTEM", "TESTT1","2","李四"));
	}
	
	@Test
	public void testInsert() {
		System.out.println(new DBScriptOutputSeviceImpl().getInsertSql("SYSTEM", "TESTT1","2","李四"));
	}
	@Test
	public void testUpdate() {
		System.out.println(new DBScriptOutputSeviceImpl().getUpdateSql("SYSTEM", "TESTT1","2","李四"));
	}
	@Test
	public void testDelete() {
		System.out.println(new DBScriptOutputSeviceImpl().getDeleteSql("SYSTEM", "TESTT1","2","李四"));
	}
	
	@Test
	public void fileWrite() {
		new DBScriptOutputSeviceImpl().scriptAppend("D:\\test.txt","hello");
	}
}
