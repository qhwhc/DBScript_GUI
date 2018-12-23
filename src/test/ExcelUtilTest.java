package test;

import org.junit.Test;

import common.util.AllBeanUtil;
import common.util.ExcelUtil;
import service.ExcelService;
import service.impl.ExcelServiceImpl;

public class ExcelUtilTest {
	private ExcelService excelService = new ExcelServiceImpl();
	@Test
	public void readObjectTest() {
		for (Object obj : ExcelUtil.getAllBeansXlsx("D:\\test.xls")) {
			System.out.println(AllBeanUtil.allBeanToString(obj));
		}
	}
	
	@Test
	public void write2ExcelTest() {
		ExcelUtil.writeXls("D:\\test.xls", ExcelUtil.readExcel("D:\\test.xlsx"));
	}
	
	
	@Test
	public void db2ExcelTest() {
		String sql = "select * from testt1 ";
		excelService.db2ExcelBySql(sql, "D:\\test1.xlsx");
	}
	@Test
	public void excel2dbTest() {
		excelService.excel2Db("testt1", "testt1", "D:\\test1.xlsx");
	}
}
