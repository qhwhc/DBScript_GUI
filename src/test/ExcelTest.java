package test;

import org.junit.Test;

import common.util.AllBeanUtil;
import dao.ExcelDao;
import dao.impl.ExcelDaoImpl;

public class ExcelTest {
	private ExcelDao excelDao = new ExcelDaoImpl();
	@Test
	public void testReadFromDb() {
		String sql = "select name from testt1";
		for (Object obj : excelDao.readFromDB(sql)) {
			System.out.println(AllBeanUtil.allBeanToString(obj));
		}
	}
	@Test
	public void testWriteToDB() {
		String sql = "select * from testt1";
		System.out.println(excelDao.write2DB("system","testt1",excelDao.readFromDB(sql)));
	}
	
}
