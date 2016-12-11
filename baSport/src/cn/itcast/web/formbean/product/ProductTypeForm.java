package cn.itcast.web.formbean.product;

import org.apache.struts.action.ActionForm;

public class ProductTypeForm extends ActionForm {

	private int page;

	public int getPage()
	{
		return page<1?1:page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}
	
	
}
