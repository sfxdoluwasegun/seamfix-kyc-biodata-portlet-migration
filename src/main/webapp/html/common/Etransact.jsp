
<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ page import="javax.portlet.RenderRequest"%>
<%@ page import="javax.portlet.RenderResponse"%>
<%@ page import="javax.portlet.PortletSession"%>
<%@ page import="javax.portlet.PortletContext"%>
<%@ page import="java.util.ResourceBundle"%>


<%
	String content = (String) request.getAttribute("content");
	content = (content == null)?"":content;
%>	
	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Etransact Payment</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<META content="DSSC, SSC, Short Service Applications" name=keywords>
<LINK href="../images/style.css" type=text/css rel=stylesheet>
<link href="../images/dssc.css" rel="stylesheet" type="text/css"> 

<style type="text/css">
<!--
.style2 {
	font-size: 10pt;
	font-weight: bold;
}
.style3 {color: #FF0000}
.style4 {
	font-size: 12pt;
	color: #FFFFFF;
}
-->
</style>
</HEAD>
<BODY bottomMargin=0 bgColor=whitesmoke leftMargin=0 topMargin=0 rightMargin=0>

<table width="100%" border=0 align="center" cellpadding=0 cellspacing=0 id=Table1> 
                  <tbody> 
                    <tr> 
                      <td width="100%" height="179" valign="top" ><table width="100%"  border="0"> 
  <tr> 
    <td bgcolor="#000066" class="cpTabSel style2"><div align="left" class="style4" style="font-size:xx-small">eTranzact</div></td> 
  </tr>
  <tr>
    <td class="cpTabSel style2"><img src="<%=response.encodeURL(request.getContextPath()+"/html/images/etranzact.jpg")%>" width="131" height="35"></td>
  </tr> 
</table> 
<table width="100%"  border="0"> 
  <tr>
    <TD><span class="style3" style="font-size:xx-small">NOTE:</span></TD>
    <TD colspan="2"><span class="style3" style="font-size:xx-small">PLEAS ENSURE THE SECURITY OF YOUR ACCOUNT DETAILS </span></TD>
  </tr>
  <tr>
    <TD width="14%">&nbsp;</TD>
    <TD colspan="2" style="font-size:xx-small">&nbsp;</TD>
  </tr>
  <form name="form1" enctype="multipart/form-data" method="post" action="<%=request.getRequestURL()%>">
   <tr>
    <td valign="top" nowrap style="font-size:xx-small">ACCOUNT NUMBER: </td>
    <td width="42%"><input name="" type="text">&nbsp;</td>  
	  <td width="44%" style="font-size:xx-small">&nbsp;</td>
  </tr>
  
  <tr>
    <td colspan="3" valign="top" align="left"><input type="button" name="Submit" value="Submit" onClick="javascript:doClosewindow()" class="cpButton"></td>
    </tr>
    </form>
  </table>
  </TBODY> 
</TABLE>
	   
</BODY>
</HTML>


<script language='javascript'>

document.domain="<%=request.getServerName()%>";

function doClosewindow(){
		try{
			 if(window.opener){
				 window.opener.refreshParentWindow();
				 self.close();
			 }
		}catch(rr){
			alert(rr.message);
		}
	}

</script> 
