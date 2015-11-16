


function search(form,value,type){

	var form = document.getElementById(form);
	var item = document.getElementById(value);
	if(type=='google'){
		form.action='http://www.google.com.hk/search'
		form.method='get'
		item.name='q'
		if(trim(item.value)!=""){
			form.submit()
		}
	} 
	if(type=='baidu'){
		form.action='http://www.baidu.com/s'
		form.method='get'
		item.name='wd'
		if(trim(item.value)!=""){
			form.submit()
		}
	}
	if(type=='fulltext'){
		form.action='http://www.oschina.net//s'
		form.method='get'
		item.name='wd'
		if(trim(item.value)!=""){
			form.submit()
		}
	}
	
}

function trim(str){ 
    return str.replace(/(^\s*)|(\s*$)/g, "");  
}  
function ltrim(str){
    return str.replace(/(^\s*)/g,"");  
}  
function rtrim(str){ 
    return str.replace(/(\s*$)/g,"");  
}

function SubmitKeyClick(obj, evt) {
	//debugger
     evt = (evt) ? evt : ((window.event) ? window.event : "")
        keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which : evt.charCode);
        if (keyCode == 13) {
           search('form','value','baidu');
        }
    }
	
function dataNotNullValidateForm(valueid){
	//debugger
	var item = document.getElementById(valueid);
	if(trim(item.value)!=""){
		return true;
	}else{
		return false;
	}
	
}

function dataNotNullValidateFormSubmit(formid,idStr){
	debugger
	var idArray = idStr.split('|');
	for(var i=0;i<idArray.length;i++){
		var id = idArray[i];
		var item = document.getElementById(id);     
		if(trim(item.value)==""){
			alert(id+" not null");
			return false; 
		}
	}
	return true
}
