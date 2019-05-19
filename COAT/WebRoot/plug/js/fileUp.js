(function($){
	$.fn.fileUp = function(o){
		o = $.extend({
			fileId : '',
			fileName : '',
			upbtnId : '',
			width : 500,
			upload : function(){
				
			}
		},o || {});
		return $(this).each(function(){
			var _that = $(this);
			var html = '<div class="fileup">'+
					   		'<div class="filecont" id="filecont"></div>'+
					   		'<input type="button" class="fileBtn" id="'+o.upbtnId+'" value="上傳"/>'+
					   		'<div class="filefunction">'+
					   			'<input type="file" name="'+o.fileName+'" id="'+o.fileId+'"/>'+
					   			'<span class="filebtn">選擇文件</span>'+
					   		'</div>'+
					   	'</div>';
			_that.append(html);
			_that.find('div.fileup').css('width',o.width);
			
			$('#'+o.fileId,this).live('change',function(){
				var _this = $(this), pathStr = _this.val().split('\\');
				var fileName = pathStr[pathStr.length-1];
				
				if(fileName.length > 0){
					$('.filecont',_that).text(fileName);
					$('.fileBtn',_that).show();
				}else{
					$('.filecont',_that).empty();
				}
			});
			
			$('input[type=button][class=fileBtn]',_that).live('click',function(){
				o.upload();
			});
			
		});
	}
})(jQuery)