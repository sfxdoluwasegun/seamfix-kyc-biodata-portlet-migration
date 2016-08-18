jQuery(document).ready(function(){			
								jQuery('select#approvalchainselect').val(''+jQuery('#approvalchainlength').val());
jQuery("#searchbutton").click(function(){
									   javascript:confirmAction('SearchUser','');
});
jQuery("#createAccount").click(function(){
									  javascript:createThisAccount();
});
jQuery(".addnewbankaccount").click(function(){
									 jQuery(".addbankaccount").slideToggle("slow")
});
jQuery("#updatemyprofile").click(function(){
										  var pass = jQuery('#password').val();
										  var retypepass = jQuery('#retypepassword').val();
										if(validateUserDetail()){
											if(pass == retypepass){
										javascript:confirmAction('UpdateUserData','');
											}else{
												alert('Password mismatch!!');
										}
										}
});

jQuery("#approvalchainselect").change(function(){
											   jQuery('#loading').show();
											   var approvalChainLenght = jQuery(this).attr("value");
											   javascript:approvalChainDefinition('ApprovalChain',jQuery('#requesturl').val(),approvalChainLenght);
											   });
jQuery(".bankdetailsretrieval").change(function(){			
											   var clickedId = jQuery(this).attr("value");
											   var imageupdated = jQuery(this).attr("name");
											   jQuery('#'+imageupdated+'_loading').show();
											   javascript:bankBranchPopulation(jQuery('#requesturl').val(),clickedId,imageupdated);
											   });

jQuery(".assign_roles").click(function(event){
	var clickedId = jQuery(this).attr("id");
	var selectWithAvailableUserRole = jQuery("#availableuserRoles_"+clickedId);
	var selectWithAssignUserRole = jQuery("#assigneduserRoles_"+clickedId);
	var userRoleIds = selectWithAvailableUserRole.val();
	if(userRoleIds == null){
		alert('Please select roles from "Available Roles" column');
		}
	else if(userRoleIds == '-1'){
		alert('Select a valid role please');
	}
	else{
		selectWithAssignUserRole.attr('disabled',true);
		selectWithAvailableUserRole.attr('disabled',true);
		jQuery('#assign_'+clickedId).find('.assign_roles').hide();
		jQuery('#assign_'+clickedId).find('.unassign_roles').hide();	
		jQuery('#loading_'+clickedId).show();
		javascript:rolesDefinition('availableuserRoles_',userRoleIds,jQuery('#requesturl').val(),clickedId);
		}	
});

jQuery(".unassign_roles").click(function(event){
	var clickedId = jQuery(this).attr("id");
	var selectWithAvailableUserRole = jQuery("#availableuserRoles_"+clickedId);
	var selectWithAssignUserRole = jQuery("#assigneduserRoles_"+clickedId);
	var userRoleIds = selectWithAssignUserRole.val();
	if(userRoleIds == null){
		alert('Please select roles from "Assigned Roles" column');
		}
	else if(userRoleIds == '-1'){
		alert('Select a valid role please');
	}
	else{
		selectWithAssignUserRole.attr('disabled',true);
		selectWithAvailableUserRole.attr('disabled',true);
		jQuery('#assign_'+clickedId).find('.assign_roles').hide();
		jQuery('#assign_'+clickedId).find('.unassign_roles').hide();	
		jQuery('#loading_'+clickedId).show();
		javascript:rolesDefinition('assigneduserRoles_',userRoleIds,jQuery('#requesturl').val(),clickedId);
		}	
			
});
});
function rolesDefinition(selectedOption,userRoleIds,requesturl,clickedId){ 
 var returned = "";
  var ajaxUrl = requesturl + '' + selectedOption + '&selectedRoles=' + userRoleIds + '&userid=' + clickedId;
  jQuery.ajax({
			url: ajaxUrl,
			type: "POST",
			dataType: "json",
			success: function(data, textStatus, jqXHR){	
			returned = new Array(data.length);
			var optionsValue = "";
			for(i = 0; i < data.length; i++){
			returned[i] = data[i].name;
			optionsValue += '<option value="' + data[i].id + '">' + data[i].name+ '</option>';
			}
			
			jQuery("#assigneduserRoles_"+clickedId).html(optionsValue);
			
			rolesRepopulation('removeUserRoles',userRoleIds,jQuery('#requesturl').val(),clickedId);
			
			jQuery("#availableuserRoles_"+clickedId).removeAttr('disabled');
			jQuery("#assigneduserRoles_"+clickedId).removeAttr('disabled');

			jQuery('#assign_'+clickedId).find('.assign_roles').show();
			jQuery('#assign_'+clickedId).find('.unassign_roles').show();	
			jQuery('#loading_'+clickedId).hide();
			
			}			
					
			});   
} 
function rolesRepopulation(selectedOption,userRoleIds,requesturl,clickedId){ 
 var returned = "";
  var ajaxUrl = requesturl + '' + selectedOption + '&selectedRoles=' + userRoleIds + '&userid=' + clickedId;
  jQuery.ajax({
			url: ajaxUrl,
			type: "POST",
			dataType: "json",
			success: function(data, textStatus, jqXHR){	
			returned = new Array(data.length);
			var optionsValue = "";
			for(i = 0; i < data.length; i++){
			returned[i] = data[i].name;
			optionsValue += '<option value="' + data[i].id + '">' + data[i].name+ '</option>';
			}
			
			jQuery("#availableuserRoles_"+clickedId).html(optionsValue);
			
			
			jQuery("#availableuserRoles_"+clickedId).removeAttr('disabled');
			jQuery("#assigneduserRoles_"+clickedId).removeAttr('disabled');

			jQuery('#assign_'+clickedId).find('.assign_roles').show();
			jQuery('#assign_'+clickedId).find('.unassign_roles').show();	
			jQuery('#loading_'+clickedId).hide();
			
			}			
					
			});   
} 
function bankBranchPopulation(requesturl,clickedId,selectedOption){ 
 var returned = "";
  var ajaxUrl = requesturl + '' + selectedOption + '&selectedId=' + clickedId;
  jQuery.ajax({
			url: ajaxUrl,
			type: "POST",
			dataType: "json",
			success: function(data, textStatus, jqXHR){	
			returned = new Array(data.length);
			var options = "";
			for(i = 0; i < data.length; i++){
			returned[i] = data[i].name;
			options += '<option value="' + data[i].id + '">' + data[i].name+ '</option>';
			}
			jQuery("#bankbranch").html(options);
				
			jQuery('#'+selectedOption+'_loading').hide();
			
			}			
					
			});   
} 
function approvalChainDefinition(selectedOption,requesturl,approvalChainLenght){ 
 var returned = "";
  var ajaxUrl = requesturl + '' + selectedOption + '&approvalChainValue=' + approvalChainLenght + '&organizationId='+jQuery('#organizationid').val();
   jQuery.ajax({
			url: ajaxUrl,
			type: "POST",
			dataType: "text",
			success: function(data, textStatus, jqXHR){				
			jQuery('#loading').hide();
			if(data == 'Success'){
				jQuery('#object').attr("class","portlet-msg-success");
			}else{
				jQuery('#object').attr("class","portlet-msg-error");
			}
			jQuery('#object').html('<strong>Status</strong><p>'+data+'</p>');
			jQuery("#object").animate({
									  top: "230px"
									  },0 ).fadeIn(3000);
			}							
			});   
} 
function validateUserDetail(){
		var reason = "";		
				reason += validateEmpty('firstname', 'First Name');
				reason += validateEmpty('surname', 'Surname');
				reason += validateEmpty('phonenumber', 'Phone Number');
				reason += validateEmpty('emailaddress', 'Email Address');	
		if(reason != "") {
			alert("You have not filled all the fields required to update your detail. They are marked in yellow:\n" + reason);
			return false;
		}else{
			return true;
		}
}
