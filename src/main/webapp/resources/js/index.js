
window.onload = function () {
	
	//登录框
	var login = S().getId('login');
	var screen = S().getId('screen');
	var w = S().getId('login').css('width');
	var h = S().getId('login').css('height');
	//alert(w+""+h);
	login.center(w.substr(0,3),h.substr(0,3)).resize(function () {
		if (login.css('display') == 'block') {
			screen.lock();
		}
	});
	S().getClass('login').click(function () {
		login.center(w.substr(0,3),h.substr(0,3));
		login.css('display', 'block');
		screen.lock();
	});
	S().getClass('close').click(function () {
		login.css('display', 'none');
		screen.unlock();
	});
	
	//拖拽
	login.drag();
	
	//var oDiv = document.getElementById('login');
	
	//拖拽流程：
	//1.先点下去
	//2.在点下的物体被选中，进行move移动
	//3.抬起鼠标，停止移动
	//点击某个物体，用oDiv即可，move和up是全局区域，也就是整个文档通用，应该用document


	

	
};
















