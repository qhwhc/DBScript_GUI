package common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
//	从Excel读取数据
	public static List<Object> readExcel(String filePath) {
		if (filePath.endsWith("xls")) return getAllBeansXls(filePath);
		if (filePath.endsWith("xlsx")) return getAllBeansXlsx(filePath);
		return null;
	}
//	讲数据写入Excel
	public static void writeExcel(String filePath,List<Object> objs) {
		if (filePath.endsWith("xls")) writeXls(filePath,objs);
		if (filePath.endsWith("xlsx")) writeXlsx(filePath,objs);
	}
	public static List<Object> getAllBeansXls(String filePath) {
		List<Object> objs = new ArrayList<Object>();
		File file = new File(filePath);
		HSSFWorkbook hssfWorkbook = null;
		if (!file.exists())
			System.out.println("文件不存在");
		try {
			// 1.读取Excel的对象
			POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
			// 2.Excel工作薄对象
			hssfWorkbook = new HSSFWorkbook(poifsFileSystem);
			// 3.Excel工作表对象
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			// 4.得到Excel工作表的行
			HSSFRow hssfRow = hssfSheet.getRow(0);
			// 总行数
			int rowLength = hssfSheet.getLastRowNum() + 1;
			// 总列数
			int colLength = hssfRow.getLastCellNum();
			Map<Object, Class<?>> properties = new HashMap<Object, Class<?>>();
			;
			for (int i = 0; i < rowLength; i++) {
//            	每个Object代表一行
				Object object = AllBeanUtil.generateObject(properties);
				// 获取Excel工作表的行
				hssfRow = hssfSheet.getRow(i);
				for (int j = 0; j < colLength; j++) {
					// 获取指定单元格
					HSSFCell hssfCell = hssfRow.getCell(j);
					if (i == 0) {
//                    	设置动态类映射
						String field = hssfCell.getStringCellValue();
						properties.put(field, Class.forName("java.lang.String"));
					} else {
						// Excel数据Cell有不同的类型，当我们试图从一个数字类型的Cell读取出一个字符串时就有可能报异常：
						// Cannot get a STRING value from a NUMERIC cell
						// 将所有的需要读的Cell表格设置为String格式
						if (hssfCell != null) {
							hssfCell.setCellType(CellType.STRING);
							// 获取每一列中的值
							Field[] fields = object.getClass().getDeclaredFields();
							AllBeanUtil.setValue(object, AllBeanUtil.getRealName(fields[j].getName()),hssfCell.getStringCellValue());
						} else {
							Field[] fields = object.getClass().getDeclaredFields();
							AllBeanUtil.setValue(object, AllBeanUtil.getRealName(fields[j].getName()), "");
						}
					}
				}
				if(i != 0) objs.add(object);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				hssfWorkbook.close();
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return objs;
	};
	public static List<Object> getAllBeansXlsx(String filePath) {
		List<Object> objs = new ArrayList<Object>();
		File file = new File(filePath);
		if (!file.exists())
			System.out.println("文件不存在");
		InputStream inputStream = null;
		Workbook workbook = null;
		try {
			inputStream = new FileInputStream(file);
			workbook = WorkbookFactory.create(inputStream);
			inputStream.close();
			// 工作表对象
			Sheet sheet = workbook.getSheetAt(0);
			// 总行数
			int rowLength = sheet.getLastRowNum() + 1;
			// 工作表的列
			Row row = sheet.getRow(0);
			// 总列数
			int colLength = row.getLastCellNum();
			// 得到指定的单元格
			Cell cell = row.getCell(0);
			// 得到单元格样式
//	            CellStyle cellStyle = cell.getCellStyle();
			Map<Object, Class<?>> properties = new HashMap<Object, Class<?>>();
			for (int i = 0; i < rowLength; i++) {
				row = sheet.getRow(i);
//            	每个Object代表一行
				Object object = AllBeanUtil.generateObject(properties);
				for (int j = 0; j < colLength; j++) {
					cell = row.getCell(j);
					if(i == 0) {
//                    	设置动态类映射
						String field = cell.getStringCellValue();
						properties.put(field, Class.forName("java.lang.String"));
					}else {
						// Excel数据Cell有不同的类型，当我们试图从一个数字类型的Cell读取出一个字符串时就有可能报异常：
						// Cannot get a STRING value from a NUMERIC cell
						// 将所有的需要读的Cell表格设置为String格式
						if (cell != null) {
							cell.setCellType(CellType.STRING);
							// 获取每一列中的值
							Field[] fields = object.getClass().getDeclaredFields();
							AllBeanUtil.setValue(object, AllBeanUtil.getRealName(fields[j].getName()),cell.getStringCellValue());
						}else {
							Field[] fields = object.getClass().getDeclaredFields();
							AllBeanUtil.setValue(object, AllBeanUtil.getRealName(fields[j].getName()), "");
						}
					}
				}
				if(i != 0) objs.add(object);
			}
			// 将修改好的数据保存
			OutputStream out = new FileOutputStream(file);
			workbook.write(out);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return objs;
	};


	public static void writeXls(String filePath,List<Object> objs) {
		HSSFWorkbook hssfWorkbook = null;
		Object value = null;
		try {
			if(objs.size() > 0) {
				hssfWorkbook = new HSSFWorkbook();
		        HSSFSheet hssfSheet = hssfWorkbook.createSheet();
				HSSFRow row = hssfSheet.createRow(0);
				Field[] fields = objs.get(0).getClass().getDeclaredFields();
//				总列数
				int colLength = fields.length;
//				总行数
				int rowLength = objs.size();
				for (int i = 0; i < colLength; i++) {
					row.createCell(i).setCellValue(AllBeanUtil.getRealName(fields[i].getName()));
				}

				for (int i = 0; i < rowLength; i++) {
					row = hssfSheet.createRow(i + 1);
//					将每一行输出
					for (int j = 0; j < fields.length; j++) {
						value = AllBeanUtil.getValue(objs.get(i),AllBeanUtil.getRealName(fields[j].getName()));
						row.createCell(j).setCellValue(value != null ? value.toString() : "");
					}
				}
			}
			
			File file = new File(filePath);
			FileOutputStream fileoutputStream = new FileOutputStream(file);
			hssfWorkbook.write(fileoutputStream);
			fileoutputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if(hssfWorkbook != null) {
					hssfWorkbook.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	public static void writeXlsx(String filePath,List<Object> objs) {
		Workbook workbook = null;
		Object value = null;
		if(objs.size() > 0) {
			workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet();
			Row row = sheet.createRow(0);
			// 设置标题
			Field[] fields = objs.get(0).getClass().getDeclaredFields();
//			总列数
			int colLength = fields.length;
//			总行数
			int rowLength = objs.size();
			for (int i = 0; i < colLength; i++) {
				row.createCell(i).setCellValue(AllBeanUtil.getRealName(fields[i].getName()));
			}

			for (int i = 0; i < rowLength; i++) {
				row = sheet.createRow(i + 1);
//				将每一行输出
				for (int j = 0; j < fields.length; j++) {
					value = AllBeanUtil.getValue(objs.get(i),AllBeanUtil.getRealName(fields[j].getName()));
					row.createCell(j).setCellValue(value != null ? value.toString() : "");
				}
			}
		}
		try {
			File file = new File(filePath);
			FileOutputStream fileoutputStream = new FileOutputStream(file);
			if(workbook != null) {
				workbook.write(fileoutputStream);
			}
			fileoutputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if(workbook != null) {
					workbook.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

}
