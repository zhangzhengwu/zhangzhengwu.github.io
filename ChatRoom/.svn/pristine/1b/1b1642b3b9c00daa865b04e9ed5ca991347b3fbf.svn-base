!function(global,factory,plus){
		factory(global,plus);
	}("undefined" != typeof window ? window : this,function(window,plus){
		var __RULES__ = {
				"require":function(){
					return this.val() && this.val().trim() !== "";
				},
				"password":function(){
					return new RegExp(this.data("bv-password")).test(this.val());
				},
				"email":function(){
					return /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(this.val());
				}
		}
		
		var __I18N__ = {
				ZH : {
					validError : "校验错误...",
					notFormError : "必须是FORM表单才能进行校验..."
				}
		};
		__I18N__.getMessage = function(lang,key){
			return this[lang]&&this[lang][key];
		}
		
		var __DEFAULTS__ = {
				raise:"change",
				lang:"ZH"
		}
		window.$.fn[plus]=function(ops){
			var $this = this;
			$.extend($this,__DEFAULTS__,ops);
			if($this.is("form")){
				var $fields = $this.find("input,select,textarea,list").not("[type=button],[type=reset],[type=submit]");
				$fields.on($this.raise,function(){
					var $field = $(this);
					var $group = $field.parents(".form-group:first").removeClass("has-error has-success");
					$group.find("span.help-block").remove();
					var result = true;
					$.each(__RULES__,function(rule,active){
						if($field.data("bv-"+rule)){
							result=active.call($field);
							$group.addClass(result?"has-success":"has-error");
							if(!result){
								$field.after("<span class=\"help-block\">"+($field.data("bv-"+rule+"-message")||__I18N__.getMessage($this.lang,"validError"))+"</span>");
								return false;
							}
						}
					});
				});
				$this.on("submit",function(){
					var $fields = $this.find("input,select,textarea,list").not("[type=button],[type=reset],[type=submit]");
					$fields.trigger($this.raise);
					if($fields.parents(".form-group").filter(".has-error").size() == 0){
						this.submit();
					}
					return false;
				});
				
			}else{
				throw new Error(__I18N__.getMessage($this.lang,"notFormError"));
			}
		}
		window.$.fn[plus].addLanguage = function(lang,map){
			__I18N__[lang] = map;
		};
		
	},"bootstrapValidator")
	
	
	
	