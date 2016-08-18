<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="javax.portlet.RenderRequest"%>
<%@ page import="javax.portlet.RenderResponse"%>
<%@ page import="javax.portlet.PortletSession"%>
<%@ page import="javax.portlet.PortletContext"%>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>

<portlet:defineObjects />

<portlet:actionURL var="gotoHomePageURL" name="gotoHomePage" />
<portlet:resourceURL var="imageURL"/>

<c:set var="portletState" value="${portletSessionScope.portletState}"/>
<c:set var="passport" value="${portletState.passport}"></c:set>
<c:set var="fingerPrintCount" value="${portletState.fingerPrintCount}"></c:set>
<c:set var="capturedSpecialData" value="${portletState.specialDataCaptured}"></c:set>
<c:set var="uid" value="${portletState.id}"></c:set>
<c:set var="demographicsData" value="${portletState.demographicsData}"></c:set>

<c:set var="contextPath" value='<%= response.encodeURL(request.getContextPath()) %>' /> 
<c:set var="imagePath" value='<%= response.encodeURL(request.getContextPath()) + "/html/images/"%>' /> <!-- to get the image folder path -->

       	<div class="uk-panel uk-panel-box uk-panel-box-primary">
			<div class="uk-panel-badge uk-badge"><a class="uk-button uk-float-right" title="GO TO HOME PAGE" href="${gotoHomePageURL}">NEW SEARCH</a> </div>
		    <h1 class="uk-panel-title">Record Details </h1>
			The record captured fully displayed.
		</div>
        
        <div id="mainDiv">
        <div align="center">
           <div>
           	<h2>PASSPORT</h2>
           </div>
           <div class="col-md-4"  style="width: 180px">             
              <a href="#" id="showPassport">	
              	<%-- <img  alt="200x200" src="data:image/jpeg;base64,<c:out value = '${passport}'/>" style="width: 200px; height: 200px;"> --%>
              </a>
           </div>
        </div>
        
        <div align="center" id="showFingerPrintCount">
        </div>
        

       <table class="uk-table uk-table-hover uk-table-striped">
            <caption>SUBSCRIBER INFORMATION</caption>
            <tbody>
		      	<c:forEach items="${demographicsData}" var="demoObj" >
		      		 <tr>
	                     <td width="30%"><c:out value = "${demoObj.name}"/></td>
	                     <td><c:out value = "${demoObj.value}"/></td>
	                 </tr>    		
		      	</c:forEach>
		      	<tr>
	            	<td width="30%"><c:out value = "SPECIAL DATA CAPTURED"/></td>
	                <td id="specialDataTd"></td>
	            </tr> 
            </tbody>
        </table>
        </div>
        
<%--         <input type="button" value="Export (PDF)" onclick="exportReport('${id}')" /> --%>
        <a class="uk-button uk-button-mini" href="#" onclick="exportReport('${uid}')" ><i class="uk-icon-download"></i> PRINT </a>
        
<script type="text/javascript">
	function exportReport(id){
		<%-- var birtUrl = '/birt/run?&__report=report/KYCBiometrics.rptdesign&UniqueID=' + id + '&__overwrite=true&__showtitle=false&__format=pdf';
		var stringUrl = '<%= request.getScheme()  + "://" + request.getServerName() + ":" + request.getServerPort()%>' + birtUrl;
		window.open(stringUrl,'session','directories=no,height=750,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,titlebar=no,toolbar=no,width=1000'); --%>

		var divToPrint=document.getElementById("mainDiv");
		newWin= window.open("");
		newWin.document.write(divToPrint.outerHTML);
		newWin.print();
		newWin.close();
	}

	//Passport
	 $(document).ready (function(){
	 	 
	 	 	$.ajax({
	 			method : "POST",
	 			url : "${imageURL}",
	 			data : {'category' : "passport"},
	 			dataType : "json",
	 			async : true,
	 			success : function(data){

					var pp = data["passport"];

					//Passport
	 				$("#showPassport").html('<img  alt=\"200x200\" src=\"data:image/jpeg;base64, ' + pp + '\" style=\"width: 200px; height: 200px;\">');

		 			},
	 			error : function (){
		 			alert("Oops something happened");
		 			}
	 	 	 	})
 	 	 	
	 	 });

	//Fingerprint
	 $(document).ready (function(){
	 	 
	 	 	$.ajax({
	 			method : "POST",
	 			url : "${imageURL}",
	 			data : {'category' : "fingerprint"},
	 			dataType : "json",
	 			async : true,
	 			success : function(data){

					var fc = data["fingerPrintCount"];
					
		 			//FingerPrint
	 				$("#showFingerPrintCount").html('<h2>FINGERPRINT(S) CAPTURED: ' + fc + '</h2>');

		 			},
	 			error : function (){
		 			alert("Oops something happened");
		 			}
	 	 	 	})
	 	 	
	 	 });

	//Special Data
	 $(document).ready (function(){
	 	 
	 	 	$.ajax({
	 			method : "POST",
	 			url : "${imageURL}",
	 			data : { 'category' : "specialData" },
	 			dataType : "json",
	 			async : true,
	 			success : function(data){

					var sd = data["specialData"];

	 				//special data
	 				$("#specialDataTd").html(sd);

		 			},
	 			error : function (){
		 			alert("Oops something happened");
		 			}
	 	 	 	})
	 	 	
	 	 });

 	 $(document)
 	 	.ajaxStart(function(){
  	 		$("#showPassport").html('<img alt="LOADING..." src="${imagePath}/loading.gif" width="150px" height="150px"/>');
 	 		$("#showFingerPrintCount").html("<i class='uk-icon-spin'></i>");
	 	 	})
	 	 	
	    .ajaxStop(function(){
	    	
			});
	
</script>