<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html>
<head>
<title>产品类别管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<script language="JavaScript">
<!--
	//到指定的分页页面
	function topage(page){
		var form = document.forms[0];
		form.page.value=page;
		form.submit();
	}
//-->
</script>
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0">
  <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
    <tr ><td colspan="6"  bgcolor="6f8ac4" align="right">
  	<!-- 页面索引开始 -->
  	
  	<c:forEach begin="${pageindex.startindex }" end="${pageindex.endindex}" var="wp">
  		第${wp }页
  	</c:forEach>
   </td></tr>
    <tr>
      <td width="8%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">代号</font></div></td>
      <td width="5%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">修改</font></div></td>
      <td width="20%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">产品类别名称</font></div></td>
	  <td width="10%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">创建下级类别</font></div></td>
	  <td width="15%" bgcolor="6f8ac4"><div align="center"><font color="#FFFFFF">所属父类</font></div></td>
	  <td nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">备注</font></div></td>
    </tr>
<!---------------------------LOOP START------------------------------>
  <c:forEach items="${productType }" var="productType">
    <tr>
      <td bgcolor="f5f5f5"> <div align="center">${productType.typeid }</div></td>
      <td bgcolor="f5f5f5"> <div align="center">
	  <input  type="image" onlick="javascript:" src="${pageContext.request.contextPath}/images/edit.gif" width="15" height="16" border="0"></a></div></td>
      <td bgcolor="f5f5f5"> <div align="center"><a href="">${productType.name}</a></div></td>
	  <td bgcolor="f5f5f5"> <div align="center"><a href="javascript:">创建子类别</a></div></td>
	  <td bgcolor="f5f5f5"> <div align="center"><c:if test="${!empty productType.parent}"><!-- 不为空的情况下才要有父类名称 -->${productType.parent.name}</c:if></div></td>
	  <td bgcolor="f5f5f5">${productType.note}</td>
	</tr>
  </c:forEach>
    <!----------------------LOOP END------------------------------->
    <tr> 
      <td bgcolor="f5f5f5" colspan="6" align="center"><table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr> 
            <td width="5%"></td>
              <td width="85%">
              <input name="AddDic" type="button" class="frm_btn" id="AddDic" onClick="javascript:window.location.href='<html:rewrite action="/control/product/type/manage"/>?method=addUI&parentid=${param.parentid}'" value="添加类别"> &nbsp;&nbsp;
			  <input name="query" type="button" class="frm_btn" id="query" onClick="javascript:window.location.href='<html:rewrite action="/control/product/type/manage"/>?method=queryUI'" value=" 查 询 "> &nbsp;&nbsp;
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</body>
</html>