package doc;

public class doc4Tool {
	public final static String TOOLFUNTION = 
			"1.数据库单个脚本生成\r\n" + 
			"2.根据Excel导入数据到数据库\r\n" + 
			"3.从数据库中导出数据到Excel文件";
	
	public final static String OPETATOR = 
			"1.配置数据库,其中当前用户为表拥有者\r\n" + 
			"2.配置文件输出路径（数据库脚本默认添加至末尾）\r\n" + 
			"3.单条记录生成\r\n" + 
			"	--->勾选需要生成的语句\r\n" + 
			"	--->输入数据库表名\r\n" + 
			"	--->主键值（联合主键以特殊符号隔开）\r\n" + 
			"	--->!@#$%^&*()+=|{}':;<>@#￥%……&*（）——+|{}【】‘；：”“’。，、？\r\n" + 
			"	--->点击备注，可在生成脚本时同时生成备注\r\n" + 
			"4.Excel导出数据\r\n" + 
			"	--->输入导出文件位置\r\n" + 
			"	--->输入SQl,即可生成\r\n" + 
			"5.Excel导入数据\r\n" + 
			"	--->输入需要导入的表格\r\n" + 
			"	--->输入导入的Excel路劲\r\n" + 
			"	--->导入后生成删除和插入的脚本位置（空则不生成）";
	public final static String NOTE = 
			"注意:\r\n" + 
			"	--->*文件导出和导入必须关闭使用的Excel文件\r\n" + 
			"	--->ps:使用中的文件不让写入" +
			"\r\nBUG:304136830@qq.com";
			
}
