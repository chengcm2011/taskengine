window.onload = function () {

    //��¼��
    var login = S().getId('login');
    var screen = S().getId('screen');
    var w = S().getId('login').css('width');
    var h = S().getId('login').css('height');
    //alert(w+""+h);
    login.center(w.substr(0, 3), h.substr(0, 3)).resize(function () {
        if (login.css('display') == 'block') {
            screen.lock();
        }
    });
    S().getClass('login').click(function () {
        login.center(w.substr(0, 3), h.substr(0, 3));
        login.css('display', 'block');
        screen.lock();
    });
    S().getClass('close').click(function () {
        login.css('display', 'none');
        screen.unlock();
    });

    //��ק
    login.drag();

    //var oDiv = document.getElementById('login');

    //��ק���̣�
    //1.�ȵ���ȥ
    //2.�ڵ��µ����屻ѡ�У�����move�ƶ�
    //3.̧����ֹ꣬ͣ�ƶ�
    //���ĳ�����壬��oDiv���ɣ�move��up��ȫ������Ҳ��������ĵ�ͨ�ã�Ӧ����document


};
















