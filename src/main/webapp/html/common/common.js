/*
 * @Author: Charlibaba
 * @Description: Common Jquery script file for common
 * 				 variable declaration and form submission
 * @Company: Seamfix Nig. Ltd
 */
//normal fom
var frmJQS;
var action;
var target;

//multipart form
var frmMulipartForm;
var maction;
var mtarget;

jQuery(document).ready(function() {
	//define the variables
	frmJQS = jQuery(".jqsForm");
	action = jQuery("#action");
	target = jQuery("#target");
	var collapsibleDiv = jQuery(".msg_body");
	if(collapsibleDiv != null){
		collapsibleDiv.hide();
	}
});

function doSubmitForm(actionValue, targetValue){
	//perform operation
	action.val(actionValue);
	target.val(targetValue);
	
	frmJQS.submit();
}

/*
function doSubmitMultipartForm(actionVal, targetVal){
	frmMultipartForm = jQuery("#multipartForm");
	maction.val(actionVal);
	mtarget.val(targetVal);
	alert("maction.val = " + maction.val());
	frmMultipartForm.submit();
	
}
*/


function requestDeleteConfirmation(){
	var confirmResponse  = confirm("Are you sure you want to delete this record?");
	if(confirmResponse==true) {
		return true;
	}else {
		return false;
	}
}

function requestMultiDeleteConfirmation(){
	var confirmResponse  = confirm("Are you sure you want to delete these selected records?");
	if(confirmResponse==true) {
		return true;
	}else {
		return false;
	}
}

function requestLogoutConfirmation(){
	var confirmResponse  = confirm("Are you sure you want to end this session and logout??");
	if(confirmResponse==true) {
		return true;
	}else {
		return false;
	}
}

function generalRequestConfirmation(confMsg){
	var confirmResponse  = confirm(confMsg);
	if(confirmResponse==true) {
		return true;
	}else {
		return false;
	}
}
//for modal window pop-ups IE & firefox variants
function showWindowInModalFormat(targetURL, intH, intW, canResize) {
	if(targetURL!=null && intH !=null && intW!=null && canResize !=null){
		if (window.showModalDialog) {
			//default alignment is center i.e. center:yes
			window.showModalDialog(targetURL,"name", "dialogWidth:" + intW + "; dialogHeight:" + intH + "; resizable:" + canResize);
		} else {
			var leftPos= 0;
			var topPos = 0;
			screenW = 480;
			screenH = 340;
			if (document.all) {
				screenW = document.body.clientWidth;
				screenH = document.body.clientHeight;
			}else if (document.layers) {
				screenW = window.innerWidth;
				screenH = window.innerHeight;
			}
			alert("screenW = " + screenW + " screenH = " + screenH);
			leftPos =(screenW - intW)/2;
			topPos = (screenH - intH)/2
			window.open(targetURL,'name', 'height=' + intH + ',width=' + intW + ',left=' + leftPos + ',top='+ topPos + ',toolbar=no,directories=no,status=no, continued from previous linemenubar=no,scrollbars=no,resizable=' + canResize +  ',modal=yes');
		}
	}
}


function showWindowInModlessFormat(targetURL, intH, intW, canResize) {
	if(targetURL!=null && intH !=null && intW!=null && canResize !=null){
		if (window.showModalDialog) {
			//default alignment is center i.e. center:yes
			window.showModalDialog(targetURL,"name", "dialogWidth:" + intW + "; dialogHeight:" + intH + "; resizable:" + canResize);
		} else {
			var leftPos= 0;
			var topPos = 0;
			screenW = 480;
			screenH = 340;
			if (document.all) {
				screenW = document.body.clientWidth;
				screenH = document.body.clientHeight;
			}else if (document.layers) {
				screenW = window.innerWidth;
				screenH = window.innerHeight;
			}
			alert("screenW = " + screenW + " screenH = " + screenH);
			leftPos =(screenW - intW)/2;
			topPos = (screenH - intH)/2
			window.open(targetURL,'name', 'height=' + intH + ',width=' + intW + ',left=' + leftPos + ',top='+ topPos + ',toolbar=no,directories=no,status=no, continued from previous linemenubar=no,scrollbars=no,resizable=' + canResize +  ',modal=yes');
		}
	}
}

function validatePhoneNumber(e){
	var src = (e.srcElement || e.target);
	var keyC = (e.keyCode || e.charCode);
			//alert(keyC);
	var key = String.fromCharCode(keyC);
	if(isNaN(key)){
				if(keyC == 8 || keyC == 43 || keyC == 45 || keyC == 37 || keyC == 38 || keyC == 39 || keyC == 40){
					return true;
				}
				else{
					return false;
				}
	}else{
				return true;
	}
	
}

function imagePopUp(img) {
	//alert("inside imagePopup function");
	htmlscriptlet = "<html><head><title>Enlarged Image</title></head><body>" +  "<IMG src='" + img + "'/>" +  "</body></html>";
	popup=window.open('','image','toolbar=0,location=0, height=600, width=800, directories=0,menuBar=1, scrollbars=1,resizable=1');
	popup.document.open();
	popup.document.write(htmlscriptlet);
	//popup.document.focus();
	popup.document.close();
	
} 