package common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ���ݿ����ӹ���
 */
public class DBConnUtil {

	public  Connection conn;
	public PreparedStatement ps;
	public ResultSet rs;
	private static  String driverClass;
	private static  String url;
	private static  String userName;
	private static  String passWord;
	
	DBConnUtil(){
		driverClass=PropertiesUtil.getValueByKey("jdbc.driverClass");
		url=PropertiesUtil.getValueByKey("jdbc.url");
		userName=PropertiesUtil.getValueByKey("jdbc.userName");
		passWord=PropertiesUtil.getValueByKey("jdbc.passWord");
	}
	
	//��ʼ������
	public static DBConnUtil init(){
		DBConnUtil dbConn=new DBConnUtil();
		//��������
		try {
			Class.forName(driverClass);
			dbConn.conn=DriverManager.getConnection(url,userName, passWord);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		//��ȡ����
	    return dbConn;
	}
	
	//���ò���
	public static void setParams(String sql,List<Object> paramList,DBConnUtil dbConn) throws Exception{
		dbConn.ps=dbConn.conn.prepareStatement(sql);
		if(paramList!=null && paramList.size()>0){
			for (int i = 0; i < paramList.size(); i++) {
				dbConn.ps.setObject(i+1, paramList.get(i));
			}
		}
		
	}
	//��ȡ���
	public static int executeUpdate(DBConnUtil dbConn,String sql,List<Object> paramList) throws Exception{
		setParams(sql, paramList, dbConn);
		return dbConn.ps.executeUpdate();//����ִ����ɾ�Ĳ���
	}
	//��ȡ���
	public static ResultSet executeQuery(DBConnUtil dbConn,String sql,List<Object> paramList) throws Exception{
		setParams(sql, paramList, dbConn);
		dbConn.rs=dbConn.ps.executeQuery();
		return dbConn.rs;
	}
	//��ʼ������
	public static List<Object> getParamList(Object...objects){
		List<Object> paramList=new ArrayList<Object>();
		for (int i = 0; i < objects.length; i++) {
			paramList.add(objects[i]);
		}
		return paramList;
	}
	
	//�����ͷ���Դ�ķ���
		public static void close(DBConnUtil dbConn){
			if(dbConn==null){
				return;
			}
			
			if(dbConn.rs!=null){
				try {
					dbConn.rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				dbConn.rs=null;
			}
			if(dbConn.ps!=null){
				try {
					dbConn.ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				dbConn.ps=null;
			}
			if(dbConn.conn!=null){
				try {
					dbConn.conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				dbConn.conn=null;
			}
		}
}
