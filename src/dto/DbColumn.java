package dto;

public class DbColumn {
	private String columnName;
	private String comments;
	private String dataLength;
	private String dataType;
	private String dataScale;
	public String getColumnName() {
		return columnName;
	}
	public DbColumn setColumnName(String columnName) {
		this.columnName = columnName;
		return this;
	}
	public String getComments() {
		return comments;
	}
	public DbColumn setComments(String comments) {
		this.comments = comments;
		return this;
	}
	public String getDataLength() {
		return dataLength;
	}
	public DbColumn setDataLength(String dataLength) {
		this.dataLength = dataLength;
		return this;
	}
	public String getDataType() {
		return dataType;
	}
	public DbColumn setDataType(String dataType) {
		this.dataType = dataType;
		return this;
	}
	public String getDataScale() {
		return dataScale;
	}
	public DbColumn setDataScale(String dataScale) {
		this.dataScale = dataScale;
		return this;
	}
	@Override
	public String toString() {
		return "DbColumn [columnName=" + columnName + ", comments=" + comments + ", dataLength=" + dataLength
				+ ", dataType=" + dataType + ", dataScale=" + dataScale + "]";
	}
	
}
