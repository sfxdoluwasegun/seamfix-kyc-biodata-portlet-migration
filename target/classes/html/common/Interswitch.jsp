
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
<TITLE>InterSwitch Payments</TITLE>
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
.style5 {
	color: #FFFFFF;
	font-size: 12pt;
	font-style: italic;
}
-->
</style>
</HEAD>
<BODY bottomMargin=0 bgColor=whitesmoke leftMargin=0 topMargin=0 rightMargin=0> 

 <table width="50%" border=0 align="center" cellpadding=0 cellspacing=0 id=Table1> 
                  <tbody> 
                    <tr> 
                      <td width="100%" height="179" valign="top" >
					  
					 
<table width="100%"  border="0"> 
  <tr> 
    <td bgcolor="#CC0033" class="cpTabSel style2"><div align="left" class="style5" style="font-size:xx-small">Interswitch</div></td> 
  </tr>
  <tr>
<td class="cpTabSel style2"><img src="<%=response.encodeURL(request.getContextPath()+"/html/images/wepdirect.PNG")%>" ></td>
  </tr> 
</table> 
<table width="100%"  border="0"> 
  <tr>
    <TD height="26"><span class="style3" style="font-size:xx-small">NOTE:</span></TD>
    <TD width="86%"><span class="style3" style="font-size:xx-small">PLEASE ENSURE THE SECURITY OF YOUR ACCOUNT DETAILS </span></TD>
  </tr>
  <tr>
    <TD width="14%">&nbsp;</TD>
    <TD style="font-size:xx-small">&nbsp;</TD>
  </tr>
  <form name="form1" enctype="multipart/form-data" method="post" action="<%=request.getRequestURL()%>">
  
  <tr>
    <td colspan="2" valign="top" align="left"><input type="button" name="Submit" value="Submit" onClick="javascript:doClosewindow()" class="cpButton"></td>
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
