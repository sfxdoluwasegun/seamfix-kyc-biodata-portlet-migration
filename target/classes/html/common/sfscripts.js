/* ********************************************************************
* JavaScript Document File for Seamfix's mostly used scripts.
* author: Busky
***********************************************************************/
function popUpWindow(stringUrl,height,width){
	//stringUrl = "<%=request.getContextPath()%>/html/portlet/applicant/application/PostUmePrintOut.jsp";
	window.open(stringUrl, 'session', 'directories=no,height='+height+	',location=no,menubar=no,resizable=no,scrollbars=no,status=no,titlebar=no,toolbar=no,width='+width);
}

function validateEmpty(fl,theDocElement) {
	var fld=document.getElementById(fl);
    var error = "";	
	if (trim(fld.value) == "") {
        fld.style.background = 'Yellow';
        error = " - The " + theDocElement + " field has not been filled!\n"; 
    }
    else if (fld.value.length == 0) {
        fld.style.background = 'Yellow'; 
        error = " - The " + theDocElement + " field has not been filled!\n"; 
    }
	else {
        fld.style.background = 'White';
    }
    return error;   
}
function validatePhone(fl) {
	var fld=document.getElementById(fl);
    var error = "";
    var stripped = fld.value.replace(/[\(\)\.\-\ ]/g, '');     
    if (fld.value == "") {
        error = " - You didn't enter a mobile phone number.\n";
        fld.style.background = 'Yellow';
    } else if (isNaN(parseInt(stripped))) {
        error = " - The mobile phone number contains illegal characters.\n";
        fld.style.background = 'Yellow';
    } else if (!(stripped.length == 13) && !(stripped.length == 11)) {
        error = " - The mobile phone number is the wrong length. Please check and retry.\n";
        fld.style.background = 'Yellow';
    } 
	else {
        fld.style.background = 'White';
    }
    return error;
}
function isEnterKeyPressed(){
		if(event.keyCode=='13'){
			nextPage();
			return true;
		}
		return false;
}
function copyTexts(fromfield,tofield,controlId ){
	var chkbttn = document.getElementById(controlId);
	var pAddr=document.getElementById(fromfield);
	var mAddr=document.getElementById(tofield);  
	
	if (chkbttn.checked == true) {          
		mAddr.value = pAddr.value;        
     }
	 else{
	 	mAddr.value = '';
	 }		
} 
function validateListButtons(fl,theListElement) {
	var error = "";
	fld = document.getElementById(fl);
	if (fld.value == 0) {
			fld.style.background = 'Yellow'; 
        error = " - The " + theListElement + " field has not been selected!\n"; 
	}else if (fld.value == null) {
			fld.style.background = 'Yellow'; 
        error = " - The " + theListElement + " field does not exist!\n"; 
	} else {
			fld.style.background = 'White';
	}
	return error;
}
function validateEmail(fl) {
	var fld=document.getElementById(fl);
    var error="";
	var tfld = trim(fld.value);                        // value of field with whitespace trimmed off
    var emailFilter = /^[^@]+@[^@.]+\.[^@]*\w\w$/ ;
    var illegalChars= /[\(\)\<\>\,\;\:\\\"\[\]]/ ;    
    if (fld.value == "") {
        fld.style.background = 'Yellow';
        error = " - You didn't enter an email address.\n";
    } else if (!emailFilter.test(tfld)) {             			 //test email for valid syntax
        fld.style.background = 'Yellow';
        error = " - Please enter a valid email address.\n";
    } else if (fld.value.match(illegalChars)) {						//test email for illegal characters
        fld.style.background = 'Yellow';
        error = " - The email address contains illegal characters.\n";
    } else {
        fld.style.background = 'White';
    }
    return error;
}
function trim(s)
{
  return s.replace(/^\s+|\s+$/, '');
}
function comparePasswords(fl,f2) {
var fld1=trim(document.getElementById(fl));
var fld2=trim(document.getElementById(f2));
var error="";
    
    if (fld1.value != fld2.value) {
        fld1.style.background = 'Yellow';
		fld2.style.background = 'Yellow';
        error = " - Your passwords don't match.\n";
    } 
	else {
        fld1.style.background = 'White';
		fld2.style.background = 'White';
    }
   return error;
}  
function validatePassword(fl,minLength,maxLength) {
	var fld=trim(document.getElementById(fl));
    var error = "";
    var illegalChars = /[\W_]/; // allow only letters and numbers 
 
    if (fld.value == "") {
        fld.style.background = 'Yellow';
        error = " - You didn't enter a password.\n";
    } else if ((fld.value.length < minLength) || (fld.value.length > maxLength)) {
        error = " - The password is the wrong length. Must be between "+ minLength + " and "+ maxLength +" characters \n";
        fld.style.background = 'Yellow';
    } else if (illegalChars.test(fld.value)) {
        error = " - The password contains illegal characters.\n";
        fld.style.background = 'Yellow';
    } else {
        fld.style.background = 'White';
    }
   return error;
}  

/* usage: onkeypress="return onlyNumberKey(event)"  */
function onlyNumberKey(e){
	var src = (e.srcElement || e.target);
	var keyC = (e.keyCode || e.charCode);
			//alert(keyC);
	var key = String.fromCharCode(keyC);
	if(isNaN(key)){
				if(keyC == 8 || keyC == 46 || keyC == 37 || keyC == 38 || keyC == 39 || keyC == 40){
					return true;
				}
				else{
					return false;
				}
	}else{
				return true;
	}
}
function validateNumberRange(fl,minNo,maxNo,eltName){
	var fld = document.getElementById(fl);
	var error = "";
	var inputVal = trim(fld.value);
    if((inputVal < minNo) || (inputVal > maxNo) || inputVal == null || inputVal == "" || isNaN(inputVal)){
		error = " - The numbers in the " + eltName + " field are out of permissible range.\n";
        fld.style.background = 'Yellow';
	}
	return error;
}
function validateCheckBox(fl,eltName) {
	var error = "";
	var fld=document.getElementById(fl);
	if(fld.checked == false){
		error = " - The " + eltName + " checkbox not selected\n";
        fld.style.background = 'Yellow';
	}
	return error;
}

/* usage: onkeypress="return imposeMaxLength(this, 15);  */
function imposeMaxLength(Object, MaxLen)
{
  return (Object.value.length <= MaxLen);
}