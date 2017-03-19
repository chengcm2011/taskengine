/**
 *
 */
var utils = utils || {};
/**
 * opt
 * {
 * 	element:,
 *  path:'',//保存路径需要把保存的文件夹放在url上
 *  data:{},//上传到后台的路劲
 *  isShow://是否预览
 * }
 */
utils.fileUpload = function (opt) {
    opt = $.extend({isShow: true}, opt);
    var element = opt.element,
        elementP = element.parent(),
        errorinfo = elementP.find("#errorinfo"),
        images = elementP.find("#images"),
        fileName = elementP.find("#fileName"),
        filePath = elementP.find("#filePath");
    return {
        'multi': opt.multi || false,
        'queueSizeLimit': 25,
        'fileSizeLimit': opt.size || "2MB",
        'swf': 'resources/js/dwz/uploadify/scripts/uploadify.swf',
        'uploader': opt.path,
        'fileObjName': 'uploadify',
        'fileTypeExts': opt.type || '*.gif; *.jpg; *.png;*.pdf;',
        'fileTypeDesc': opt.typeText || 'gif,jpg,png,pdf',
        'successTimeout': 99999,
        'uploadLimit': 1000,
        'buttonText': '请选择文件',
        'width': 94,
        'height': 29,
        'formData': opt.data || {},
        'onSelectError': function (file, errorCode, errorMsg) {
            errorinfo.show();
            switch (errorCode) {
                case -100:
                    errorinfo.html("上传的文件数量已经超出系统限制的" + element.uploadify('settings', 'queueSizeLimit') + "个文件！");
                    break;
                case -110:
                    errorinfo.html("文件 [" + file.name + "] 超出系统限制的" + element.uploadify('settings', 'fileSizeLimit') + "大小！");
                    break;
                case -120:
                    errorinfo.html("文件 [" + file.name + "] 大小异常！");
                    break;
                case -130:
                    errorinfo.html("文件 [" + file.name + "] 类型不正确！");
                    break;
            }
            return false;
        },
        'onUploadError': function (file, errorCode, errorMsg, errorString) {
            return false;
        },
        'onUploadSuccess': function (file, data, response) {
            errorinfo.hide();
            var dataJson = $.parseJSON(data);
            filePath.val(dataJson.mapData.filename);
            images.attr("src", "http://127.0.0.1:8080/pipp/" + dataJson.mapData.filename);
            return false;
        },
        'onSelect': function (file) {
            errorinfo.hide();
            fileName.text(file.name);
        }
    };
};
