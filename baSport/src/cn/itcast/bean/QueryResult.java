package cn.itcast.bean;

import java.util.List;

/**
 * ÿһҳ�ļ�¼��
 * @author ����
 *
 * @param <T> ����ʵ���������
 */
public class QueryResult<T> {
	
	/**��ѯ�����*/
	private List<T> resultlist;
	
	/**�ܼ�¼��*/
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
