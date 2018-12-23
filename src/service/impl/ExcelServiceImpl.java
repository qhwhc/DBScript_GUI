package service.impl;

import dao.ExcelDao;
import dao.impl.ExcelDaoImpl;
import service.ExcelService;

public class ExcelServiceImpl implements ExcelService {
	private ExcelDao excelDao = new ExcelDaoImpl();
	@Override
	public int db2ExcelBySql(String sql, String filePath) {
		return excelDao.write2Excel(filePath, excelDao.readFromDB(sql));
	}

	@Override
	public String excel2Db(String user, String tableName, String filePath) {
		return excelDao.write2DB(user, tableName, excelDao.readFromExcel(filePath));
	}

}
