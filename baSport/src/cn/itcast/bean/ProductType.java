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
 * 实现序列化接口(用于网络传输)
 * 重载hasCode和equals方法,判断两个对象是否相等,一般用主键判断*/
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
	 * 类别名称
	 * @return
	 */
	private String name;
	
	/**
	 * google 描述
	 * @return
	 */
	private String note;
	
	/**
	 * 是否可见
	 * @return
	 */
	private Boolean visible=true;
	
	/**
	 * 子类别
	 * @return
	 */
	private Set<ProductType> childtype=new HashSet<ProductType>();
	
	/**
	 * 父类别
	 * @return
	 */
	private ProductType parent;
	
	/*级联操作,如果对这个实体bean调用Refresh方法,就会对底下的子类别刷新操作
	 *如果对父类调用删除操作,它也会对底下的子类别进行删除操作
	 *双向关系要定义维护方和被维护方
	 *在一对多中,就在一的一方定义成被维护端,维护端就是多的一方,哪个属性进行映射关系的维护(parent)*/
	@OneToMany(cascade={CascadeType.REFRESH,CascadeType.REMOVE},mappedBy="parent", fetch=FetchType.EAGER)
	public Set<ProductType> getChildtype() {
		return childtype;
	}

	public void setChildtype(Set<ProductType> childtype) {
		this.childtype = childtype;
	}

	/*optional表示是否一定要对这个属性赋值
	 * 默认是true,可以不存在*/
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="parentid")
	public ProductType getParent() {
		return parent;
	}

	public void setParent(ProductType parent) {
		this.parent = parent;
	}

	/*让数据库自动确定增长类型,如果是Identity,则是自动增长*/
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
