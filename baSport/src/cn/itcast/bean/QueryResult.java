package cn.itcast.bean;

import java.util.List;

/**
 * 每一页的记录数
 * @author 海峰
 *
 * @param <T> 具体实体类的类型
 */
public class QueryResult<T> {
	
	/**查询结果集*/
	private List<T> resultlist;
	
	/**总记录数*/
	private long totalRecord; 
	
	
	
	public List<T> getResultlist() {
		return resultlist;
	}
	public void setResultlist(List<T> resultlist) {
		this.resultlist = resultlist;
	}
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}

}
