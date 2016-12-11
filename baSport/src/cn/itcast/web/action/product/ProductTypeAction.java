package cn.itcast.web.action.product;

import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.stereotype.Controller;

import cn.itcast.bean.PageIndex;
import cn.itcast.bean.ProductType;
import cn.itcast.bean.QueryResult;
import cn.itcast.bean.WebTool;
import cn.itcast.service.product.ProductTypeService;
import cn.itcast.web.formbean.product.ProductTypeForm;

@Controller("/control/product/type/list")
// <---指定bean的名称
public class ProductTypeAction extends Action {

	@Resource(name = "productTypeServiceImpl")
	private ProductTypeService productTypeService;

	@Override	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProductTypeForm formbean = (ProductTypeForm) form;
//		每一页12条记录
		int maxresult = 12;
		//每一页的第一条记录
		int firstindex = (formbean.getPage() - 1) * maxresult;
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("typeid", "desc");
		QueryResult<ProductType> qr = productTypeService.getScrollData(
				ProductType.class, firstindex, maxresult, "o.visible=1?",
				new Object[] { true }, orderby);
		//总页数,如果总记录数/每一页的记录的余数为0,则为那么多页,否则就多一页
		long totalpage=qr.getTotalRecord()%maxresult==0?qr.getTotalRecord()/maxresult:qr.getTotalRecord()/maxresult+1;
		PageIndex pageindex=WebTool.getPageIndex(maxresult, formbean.getPage(), totalpage);
		request.setAttribute("productType", qr.getResultlist());
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("pageindex", pageindex);
		return mapping.findForward("list");
	}
}
