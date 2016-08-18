<%@page import="com.sf.lfa.msg.MessageType"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="cc" uri="http://java.sun.com/jsp/jstl/core"%>
<cc:if test="${not empty alertMsgs }">
	<cc:forEach var="msgType" items="<%=MessageType.values()%>">
		<cc:forEach items="${requestScope.alertMsgs }" var="msg">
			<cc:if test="${msg.messageType eq msgType }">
				<div class="uk-alert uk-alert-${fn:toLowerCase(msgType) }">
					${msg.message}</div>
			</cc:if>
		</cc:forEach>
	</cc:forEach>
</cc:if>