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
// <---ָ��bean������
public class ProductTypeAction extends Action {

	@Resource(name = "productTypeServiceImpl")
	private ProductTypeService productTypeService;

	@Override	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ProductTypeForm formbean = (ProductTypeForm) form;
//		ÿһҳ12����¼
		int maxresult = 12;
		//ÿһҳ�ĵ�һ����¼
		int firstindex = (formbean.getPage() - 1) * maxresult;
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("typeid", "desc");
		QueryResult<ProductType> qr = productTypeService.getScrollData(
				ProductType.class, firstindex, maxresult, "o.visible=1?",
				new Object[] { true }, orderby);
		//��ҳ��,����ܼ�¼��/ÿһҳ�ļ�¼������Ϊ0,��Ϊ��ô��ҳ,����Ͷ�һҳ
		long totalpage=qr.getTotalRecord()%maxresult==0?qr.getTotalRecord()/maxresult:qr.getTotalRecord()/maxresult+1;
		PageIndex pageindex=WebTool.getPageIndex(maxresult, formbean.getPage(), totalpage);
		request.setAttribute("productType", qr.getResultlist());
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("pageindex", pageindex);
		return mapping.findForward("list");
	}
}
