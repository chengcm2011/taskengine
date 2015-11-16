/// 
function dataNotNullValidateForm(idStr,idStr2){
	//debugger
	var idArray = idStr.split('|');
	for(var i=0;i<idArray.length;i++){
		var comp = document.getElementById(idArray[i]) 
		var id = comp.id ;
		var value = comp.value ;
		if(value==""||value==null){
			document.getElementById(id+'_error').style.display='block';
			return false; 
		}else{
		    document.getElementById(id+'_error').style.display='none';
		}
		debugger
		if(id=='username'){
			if(!mailValidate(comp)){
				document.getElementById(id+'_patternerror').style.display='block';
				return false; 
			}else{
			    document.getElementById(id+'_patternerror').style.display='none';
			}
			
		}
	}
	debugger
	if(cofirm(idStr2)){
		return false; 
	}
	return true
}
function cofirm(idStr){
	debugger
	var idArray = idStr.split('|');
	debugger
	var p0 = document.getElementById(idArray[0]) ;
	var p1 = document.getElementById(idArray[1]) ;
	if(p0.value==p1.value){
		document.getElementById(p0.id+'_rep').style.display='none';
		document.getElementById(p1.id+'_rep').style.display='none';
		return false; 
	}else{
		document.getElementById(p0.id+'_rep').style.display='block';
		document.getElementById(p1.id+'_rep').style.display='block';
		return true
	}
}
function valueCheck(object){
	//debugger
	if(NullValidate(object)){
		 // 
		document.getElementById(object.id+'_error').style.display='block';
	}else{
		//debugger
		debugger
		if(object.id=='username'){
			if(!mailValidate(object)){
				document.getElementById(object.id+'_patternerror').style.display='block';
			}else{
				document.getElementById(object.id+'_patternerror').style.display='none';
			}
			uniquecheck(object);
		}
		document.getElementById(object.id+'_error').style.display='none';
	}

}

function mailValidate(object){
	debugger
	var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if(myreg.test(object.value)){
        return true;
    }
	return false;
}
function NullValidate(object){
	var value = object.value
	if(value ===null||value ===""){
		return true;
	}else{
		return false;
	}
}
 


function uniquecheck(obj){
	debugger
	var url = "register?"+"type=unique&value="+obj.value;
	var temp = encodeURI(url)
	if(window.XMLHttpRequest) {
			req = new XMLHttpRequest();
	} 
	req.open("get", temp, true);
	req.onreadystatechange = function()
	{
		if(req.readyState == 4) {
			if(req.status == 200) {
				var result = req.responseText;
				if(result=='success'){
					document.getElementById('username_patternerror1').style.display='none'
					return true;
					//alert()
					
				}else if(result=='error'){
					document.getElementById('username_patternerror1').style.display='block'
					return false;
					//alert()
					
				}
			}
		}
		
	};
	req.send(null);		
}