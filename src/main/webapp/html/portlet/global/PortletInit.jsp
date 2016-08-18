<%
/*
 * Copyright (c) SeamFix Solutions Ltd
 */

%>
<%@ page import="javax.portlet.RenderRequest"%>
<%@ page import="javax.portlet.RenderResponse"%>
<%@ page import="javax.portlet.PortletSession"%>
<%@ page import="javax.portlet.PortletContext"%>
<%@ page import="java.util.Iterator" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<portlet:defineObjects />

<link type="text/css" rel="stylesheet" href='<%=response.encodeURL(request.getContextPath()+ "/html/common/facebox.css")%>'/>

<script src="<%=response.encodeURL(request.getContextPath()+ "/html/common/jquery.js")%>" type="text/javascript"></script>
<script src="<%=response.encodeURL(request.getContextPath()+ "/html/common/jquery.validate.js")%>" type="text/javascript"></script>
<script src="<%=response.encodeURL(request.getContextPath()+ "/html/common/jquery.metadata.js")%>" type="text/javascript"></script>
<script src="<%=response.encodeURL(request.getContextPath()+ "/html/common/jquery.jqprint.js")%>" type="text/javascript"></script>
<script src="<%=response.encodeURL(request.getContextPath()+ "/html/common/sfscripts.js")%>" type="text/javascript"></script>
<script src="<%=response.encodeURL(request.getContextPath()+ "/html/common/jcap.js")%>" type="text/javascript"></script>
<script src="<%=response.encodeURL(request.getContextPath()+ "/html/common/md5.js")%>" type="text/javascript"></script>
<script src="<%=response.encodeURL(request.getContextPath()+ "/html/common/common.js")%>" type="text/javascript"></script>
<script src="<%=response.encodeURL(request.getContextPath()+ "/html/common/paging.js")%>" type="text/javascript"></script>
<script src="<%=response.encodeURL(request.getContextPath()+ "/html/common/facebox.js")%>" type="text/javascript"></script>

<script type='text/javascript' src='<%=response.encodeURL(request.getContextPath()+ "/dwr/interface/NodeMgmtAjax.js") %>'></script>
<script type='text/javascript' src='<%=response.encodeURL(request.getContextPath()+ "/dwr/engine.js") %>'></script>
<script type='text/javascript' src='<%=response.encodeURL(request.getContextPath()+ "/dwr/util.js") %>'></script>
<%
	PortletContext portletContext = portletConfig.getPortletContext();
%>



<script language="javascript" type="text/javascript"> 

	function <portlet:namespace/>doSubmit(formId, action, target, button) {
	
		var theform = document.getElementById(formId);
		if(button != ""){
            button.setAttribute("disabled", "disabled");
        }
		theform.action.value = action;
		theform.target.value = target;
		
		theform.submit();
	}

	function <portlet:namespace/>doSubmit(formId, action, target) {
	//alert('form id '+formId);
		var theform = document.getElementById(formId);
		theform.action.value = action;
		theform.target.value = target;
		//alert ("the submit method has been called the action is: "+action+" ;the target nodeid is  "+target+" the form called is : "+theform.name );
		//alert('form action '+action);
		theform.submit();
	}

	
	function endApplication(){
		if(confirm('Save and exit? Please confirm:')!= 0){	
			location.href="http://<%=request.getServerName()%>:<%=request.getServerPort()%>/c/portal/logout";
		}
	}
	
	
	jQuery(function() {
	  jQuery('a[rel*=facebox]').facebox({
	        closeImage   : '<%=response.encodeURL(request.getContextPath()+ "/html/images/closelabel.png")%>',
	        loadingImage   : '<%=response.encodeURL(request.getContextPath()+ "/html/images/loading.gif")%>',
		    opacity: 0.7
	      });
	});

	jQuery(document).bind('afterReveal.facebox', function() {
		var windowHeight = jQuery(window).height();
		//var windowWidth = jQuery(window).width();
		var faceboxHeight = jQuery('#facebox').height();
		//jQuery('#facebox').width(windowWidth/2);
		if(faceboxHeight < windowHeight) {
			jQuery('#facebox').css('top', (Math.floor((windowHeight - faceboxHeight) / 2) + jQuery(window).scrollTop()) );
		}
	});			

	jQuery(function() {
				//jQuery("#loadstatus").fadeOut("slow");
				//jQuery("#showpage").fadeIn("slow");
				jQuery("#loadstatus").hide();
				jQuery("#showpage").show();
		  });

</script>

<style>
	#<portlet:namespace/>Form .invalidField{
		background: yellow;
	}
</style>

<script type="text/javascript">


		jQuery.validator.addMethod( 
			"selectNone", 
			function(value, element, params) {
				for(var i = 0; i < params.length; i++)
				{
					if (element.value == params[i])
					{
						return false;
					}
				}
				return true; 
			}, 
			"Please select an option." 
		);
	
	
	function paginatorSubmit(action){
		var theform = document.getElementById('<portlet:namespace/>Form');
		theform.action.value = action;
		theform.submit();
		}


function validateForm (submitAction, submitTarget, clickedButton) {
		jQuery("#<portlet:namespace/>Form").validate({
		 	errorContainer: "#messageBox1",
		 	errorLabelContainer: jQuery("ul", "#messageBox1"),
		    wrapper: 'li',
		    highlight: function(element, errorClass) {
		        jQuery(element).addClass("invalidField");
		     },
		     unhighlight: function(element, errorClass) {
		        jQuery(element).removeClass("invalidField");},
		    submitHandler: function() {
		    	alert("The form is to be submitted now : " + submitAction + "  " + submitTarget);
				<portlet:namespace/>doSubmit('<portlet:namespace/>Form',submitAction,submitTarget,clickedButton);
		    }
		});
		jQuery("#<portlet:namespace/>Form").submit();
	}
	

	function validateForm (submitAction, submitTarget) {
		jQuery("#<portlet:namespace/>Form").validate({
		 	errorContainer: "#messageBox1",
		 	errorLabelContainer: jQuery("ul", "#messageBox1"),
		    wrapper: 'li',
		    highlight: function(element, errorClass) {
		        jQuery(element).addClass("invalidField");
		     },
		     unhighlight: function(element, errorClass) {
		        jQuery(element).removeClass("invalidField");},
		    submitHandler: function() {
				<portlet:namespace/>doSubmit('<portlet:namespace/>Form',submitAction,submitTarget);
		    }
		});
		jQuery("#<portlet:namespace/>Form").submit();
	}

	function validateMulitpartForm (submitAction, submitTarget) {
		jQuery("#multipartForm").validate({
		 	errorContainer: "#messageBox1",
		 	errorLabelContainer: jQuery("ul", "#messageBox1"),
		    wrapper: 'li',
		    highlight: function(element, errorClass) {
		        jQuery(element).addClass("invalidField");
		     },
		     unhighlight: function(element, errorClass) {
		        jQuery(element).removeClass("invalidField");},
		    submitHandler: function() {
		        	doSubmitMultipartForm(submitAction,submitTarget);
		    }
		});
		jQuery("#multipartForm").submit();
	}

	function doSubmitMultipartForm(actionVal, targetVal){
		frmMultipartForm =   document.getElementById("multipartForm");
		frmMultipartForm.maction.value = actionVal;
		frmMultipartForm.mtarget.value  = targetVal;
		
		frmMultipartForm.submit();
		
	}
	
	function validateForm2 (submitAction, submitTarget, clickedButton) {
		jQuery("#<portlet:namespace/>Form1").validate({
		 	errorContainer: "#messageBox1",
		 	errorLabelContainer: jQuery("ul", "#messageBox1"),
		    wrapper: 'li',
		    highlight: function(element, errorClass) {
		        jQuery(element).addClass("invalidField");
		     },
		     unhighlight: function(element, errorClass) {
		        jQuery(element).removeClass("invalidField");},
		    submitHandler: function() {
				<portlet:namespace/>doSubmit('<portlet:namespace/>Form1',submitAction,submitTarget,clickedButton);
		    }
		});
		jQuery("#<portlet:namespace/>Form1").submit();
	}

	function validateForm2(submitAction, submitTarget, clickedButton) {
		jQuery("#<portlet:namespace/>Form1").validate({
		 	errorContainer: "#messageBox1",
		 	errorLabelContainer: jQuery("ul", "#messageBox1"),
		    wrapper: 'li',
		    highlight: function(element, errorClass) {
		        jQuery(element).addClass("invalidField");
		     },
		     unhighlight: function(element, errorClass) {
		        jQuery(element).removeClass("invalidField");},
		    submitHandler: function() {
				<portlet:namespace/>doSubmit('<portlet:namespace/>Form1',submitAction,submitTarget,clickedButton);
		    }
		});
		jQuery("#<portlet:namespace/>Form1").submit();
	}
	
</script>

<div class="portlet-msg-alert" id="messageBox1" style="display: none">
	One or more fields have not been correctly filled
	<ul></ul>
</div>

<script language="javascript">
	jQuery(function() {
				//jQuery("#loadstatus").fadeOut("slow");
				//jQuery("#showpage").fadeIn("slow");
				jQuery("#loadstatus").hide();
				jQuery("#showpage").show();
		  });
</script>

<table id="loadstatus" width="100%" border="0">
 <tr >
<td align="center" valign="middle"><img align="absmiddle" src="<%=response.encodeURL(request.getContextPath()+ "/html/images/loading3.gif")%>" alt="your request is  being processed." width="25" height="25" /> <strong>Loading... Please wait </strong></td>
  </tr>
</table>
