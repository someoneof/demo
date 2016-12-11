package cn.itcast.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


/*
 * ʵ�����л��ӿ�(�������紫��)
 * ����hasCode��equals����,�ж����������Ƿ����,һ���������ж�*/
@Entity
public class ProductType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Integer typeid;

	/**
	 * �������
	 * @return
	 */
	private String name;
	
	/**
	 * google ����
	 * @return
	 */
	private String note;
	
	/**
	 * �Ƿ�ɼ�
	 * @return
	 */
	private Boolean visible=true;
	
	/**
	 * �����
	 * @return
	 */
	private Set<ProductType> childtype=new HashSet<ProductType>();
	
	/**
	 * �����
	 * @return
	 */
	private ProductType parent;
	
	/*��������,��������ʵ��bean����Refresh����,�ͻ�Ե��µ������ˢ�²���
	 *����Ը������ɾ������,��Ҳ��Ե��µ���������ɾ������
	 *˫���ϵҪ����ά�����ͱ�ά����
	 *��һ�Զ���,����һ��һ������ɱ�ά����,ά���˾��Ƕ��һ��,�ĸ����Խ���ӳ���ϵ��ά��(parent)*/
	@OneToMany(cascade={CascadeType.REFRESH,CascadeType.REMOVE},mappedBy="parent", fetch=FetchType.EAGER)
	public Set<ProductType> getChildtype() {
		return childtype;
	}

	public void setChildtype(Set<ProductType> childtype) {
		this.childtype = childtype;
	}

	/*optional��ʾ�Ƿ�һ��Ҫ��������Ը�ֵ
	 * Ĭ����true,���Բ�����*/
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="parentid")
	public ProductType getParent() {
		return parent;
	}

	public void setParent(ProductType parent) {
		this.parent = parent;
	}

	/*�����ݿ��Զ�ȷ����������,�����Identity,�����Զ�����*/
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	

	@Column(length=36,nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length=200)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(nullable=false)
	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeid == null) ? 0 : typeid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductType other = (ProductType) obj;
		if (typeid == null) {
			if (other.typeid != null)
				return false;
		} else if (!typeid.equals(other.typeid))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "ProductType [typeid=" + typeid + ", name=" + name + ", note="
				+ note + ", visible=" + visible + ", childtype=" + childtype
				+ ", parent=" + parent + "]";
	}
	
	
	
}
