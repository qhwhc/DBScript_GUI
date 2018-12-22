package dto;

import java.util.List;

public class DbTable {
	private String tableName;
	private String comment;
	private String primaryKey;
	private List<DbColumn> columns;
	
	public String getTableName() {
		return tableName;
	}
	public DbTable setTableName(String tableName) {
		this.tableName = tableName;
		return this;
	}
	public String getComment() {
		return comment;
	}
	public DbTable setComment(String comment) {
		this.comment = comment;
		return this;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public DbTable setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
		return this;
	}
	public List<DbColumn> getColumns() {
		return columns;
	}
	public DbTable setColumns(List<DbColumn> columns) {
		this.columns = columns;
		return this;
	}
	@Override
	public String toString() {
		return "DbTable [tableName=" + tableName + ", comment=" + comment + ", primaryKey=" + primaryKey + ", columns="
				+ columns + "]";
	}
}
