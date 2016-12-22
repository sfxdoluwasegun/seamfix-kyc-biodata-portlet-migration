<%@ page import="javax.portlet.RenderRequest"%>
<%@ page import="javax.portlet.RenderResponse"%>
<%@ page import="javax.portlet.PortletSession"%>
<%@ page import="javax.portlet.PortletContext"%>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>

<portlet:defineObjects />

<portlet:renderURL var="renderTable"/>
<portlet:actionURL var="searchBioDataURL" name="searchBioData" />
<portlet:actionURL var="resetPageSizeURL" name="resetPageSize" />

<c:set var="portletState" value="${portletSessionScope.portletState}"/>
<c:set var="pageSize" value="${portletState.pageSize}" />
<c:set var="phoneNumber" value="${portletState.phoneNumber}"/>
<c:set var="subscriberName" value="${portletState.name}"/>
<c:set var="uniqID" value="${portletState.uniqueID}"/>

<c:set var="contextPath" value='<%= response.encodeURL(request.getContextPath()) %>' /> 
<c:set var="imagePath" value='<%= response.encodeURL(request.getContextPath()) + "/html/images/"%>' /> <!-- to get the image folder path -->

	   <div class="uk-grid" data-uk-grid-margin>
           <div class="uk-width-large-1-1 uk-visible-large uk-row-first">
               	<div class="uk-panel">
              			<!-- Search -->
                    <form class="uk-form uk-margin" name="search" action="${searchBioDataURL}" method="post">
                        <fieldset data-uk-margin>
                            <legend>Search</legend>
                                    <div class="uk-form-row">
                                        <input type="text" name="name" class="uk-margin-small-top" placeholder="Enter name" value="${subscriberName}" />
                                        <input type="number" max="99999999999" min="0" name="phoneNumber" class="uk-margin-small-top" placeholder="Enter phone number" value="${phoneNumber}" />
                                        <input type="text" name="uniqueID" class="uk-margin-small-top" placeholder="Enter unique ID or Sim Serial" style="width: 350px" value="${uniqID}" />
                                        <a class="uk-button uk-button-primary" title="Search" href="javascript:document.search.submit();"><i class="uk-icon-search"></i></a>                      
                                    </div>
                        </fieldset>
                    </form>
               	</div>
            </div>
        </div>
      
        <div style="clear:both"></div>
        
        <c:choose>
		<c:when test="${portletState.biodata ne null}">
			<c:set var="biodataList" value="${portletState.biodata}" />
		</c:when>
		<c:otherwise>
			<c:set var="biodataList" value="${portletState.initialState.biodata}" />
		</c:otherwise>
	</c:choose>
        
        <c:if test="${biodataList ne null }" >
	        <p>&nbsp;</p>
	        <div>
	            <!--Page List -->
	            <div style="float: left;">
	            	<form class="uk-form uk-margin" name="pageSizeForm" id="pageSizeForm" method="post" action="${resetPageSizeURL}">
		                <div class="uk-form-row">
		                    <select name="pageSize" id="pageSize" >
								<option value="5" ${ (pageSize eq 5) ? "selected" : "" }>5</option>
								<option value="10" ${ (pageSize eq 10) ? "selected" : "" }>10</option>
								<option value="25" ${ (pageSize eq 25) ? "selected" : "" }>25</option>
								<option value="50" ${ (pageSize eq 50) ? "selected" : "" }>50</option>
								<option value="100" ${ (pageSize eq 100) ? "selected" : "" }>100</option> 
		                    </select>
		                    <a class="uk-button uk-button-medium uk-button-primary" title="Click here to reset the number of kits displayed" 
								id="resetRecords" onclick="document.pageSizeForm.submit()" href="#"><i class="uk-icon-refresh"></i>
							</a>
		                </div>
	                </form>
	            </div>
	        </div>
        </c:if>
        
        <div style="clear:both"></div>
        <p>&nbsp;</p>
        
	<c:choose>  
		<c:when test="${biodataList ne null }"> 
	        <!-- table containing the details -->
			<fieldset>
				<display:table id="Information" name="${biodataList}" pagesize="${pageSize}" class="uk-table uk-table-hover uk-table-striped uk-table-condensed" sort="page" requestURI="${renderTable}" >  
				
					<c:set var="fullName" value="${Information.customerName}"></c:set>
					<c:set var="phoneNumber" value="${Information.phoneNumber}"></c:set>
					<c:set var="uniqueID" value="${Information.uniqueId}"></c:set>
					<c:set var="registrationDate" value="${Information.registrationTimestamp}"></c:set>
					<c:set var="id" value="${Information.basicDataId}"></c:set>
					<c:set var="serialNo" value="${Information.serialNumber}"></c:set>
	        		
	        		<portlet:actionURL var="gotoBioViewPageURL" name="gotoBioViewPage" >
	          			<portlet:param name="bID" value="${id}"/>
	        		</portlet:actionURL>
	        		
					<display:column title="NAME">
						<c:out value="${fullName}"></c:out>
					</display:column>
					
					<display:column title="PHONE NUMBER">
						<c:out value="${phoneNumber}"></c:out>
					</display:column>	
					
					<display:column title="UNIQUE ID">
						<c:out value="${uniqueID}"></c:out>
					</display:column>								
	
					<display:column title="REGISTRATION DATE">
                                                 <c:choose>
                                                    <c:when test='${registrationDate eq null }'>
                                                        N/A
                                                    </c:when>
                                                    <c:otherwise>
                                                        <fmt:formatDate value="${registrationDate}" pattern="dd-MMM-yyyy" />
                                                    </c:otherwise>
                                                </c:choose>
					</display:column>	
					
					<display:column title="SERIAL NUMBER">
						<c:out value="${serialNo}"></c:out>
					</display:column>		
					
					<display:column title="">              	          			
						<a class="uk-button uk-button-success uk-float-right" title="View bio-metrics" href="${gotoBioViewPageURL}">VIEW</a>  		              
			        </display:column>
			        
					<!--<display:setProperty name="basic.empty.showtable" value="false" /> -->
				</display:table>
			</fieldset>
		</c:when>
		<c:otherwise>
			<div class="uk-panel uk-panel-box uk-panel-box-primary">
				<div class="uk-panel-badge uk-badge">INFO</div>
			    <h3 class="uk-panel-title">BIO-DATA REPORT MODULE</h3>
				This Module allows users to search for Subscribers details using <b>either Subscriber Name, Subscriber Mobile Number (MSISDN) 
				, Unique ID or SIM SERIAL </b> of the record. None of the parameters are mandatory or dependent on the other, it's an 'either or' requirement. 
			</div>
		</c:otherwise>
	</c:choose>    
