<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<portlet:defineObjects />

<portlet:renderURL var="render"/>

<jsp:include page="/html/common/header.jsp"/>
<jsp:include page="/html/common/alert.jsp"/>

<c:set value="${portletSessionScope.portletState}" var="portletState"/>

<div class="mtn-kyc" >
	<c:choose>
		<c:when test="${not empty portletState and portletState.currentView ne null}">
			<jsp:include page="${portletState.currentView.jsp }"/>
		</c:when>
	</c:choose>
</div>