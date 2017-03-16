ImageUploader = {
		$load : null,    // jquery object for loading icon
		$modalImg : null,   // jquery object for image that need be cropped.
		$lnkUploadFile : null, // link to activate the image uploader.
		dialog : null,      //  the reference to the dialog control.
		cropOpt :  null,    // the image cropped cordinate data.
		url : null,         //  the image upload action url.
		flashUploadURL : null,
		options : null,
		flashCtrl : null,     //  reference to the flash object.
		flashAlternateContent : null,
		downloadURL:null,
		
		dlgTemplateString: '<div>' +
								'<div id="file_upload_dialog" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="fileUploadlLabel">' + 
									'<div class="modal-dialog fileUploaderDialog" role="document">' +
										'<div class="modal-content">' +
											'<div class="modal-header">' + 
												'<button type="button" class="close" data-dismiss="modal" aria-label="关闭"><span aria-hidden="true">&times;</span></button>' +
												'<h4 class="modal-title" id="fileUploadlLabel"></h4>' +
											'</div>' +
											'<div class="modal-body">' +
												'<div class="upload-photo">' + 
													'<div class="default_img text-center">' +
														'<img class="file-upload-image" id="wcc-img-default_img" width="160" height="160" src=""/>' +
													'</div>' +
													'<div class="hide flashWrapper text-center" id="takePhotoWrapper"></div>' +
													'<div id="inline_error_container" class="hide error-container">' +
														'<p id="wcc-txt-inline_error" style="margin-top: 10px; color: red;" class="form-error form-length-l text-center" aria-live="polite">' +
															'<i></i><span></span>' +
														'</p>' +
														'<div class="form-gutter-m">&nbsp;</div>' +
													'</div>' +
													'<div id="wcc-txt-text" class="tipContainer text-center" style="margin-top:10px;">' +
														'<div id="photoTip1">' +
															'图片尺寸建议至少为160 X 160像素，合法图片格式包括jpeg，gif，png三种，图片大小最大为5MB' +
														'</div>' +
														'<div id="photoTip2" class="hide">' +
															'<p>您可以继续对图片进行裁剪</p>' +
														'</div>' +
														'<div id="photoTip3" class="hide">' +
															'拍照需要Flash Player 11.1及以上版本，请确保您的浏览器安装并开启了此插件，您也可以从<a target="_blank" href="http://get.adobe.com/flashplayer/">这里</a>下载最新版本' +
														'</div>' +
														'<div class="form-gutter-m">&nbsp;</div>' +
													'</div>' +
													'<div class="verticalBtnGroup">' +
														'<button class="btn btn-success wide_btn hide" type="button" id="wcc-btn-save">保存</button>' +
														'<button class="btn btn-default wide_btn hide" type="button" id="wcc-btn-cancel">取消</button>' +
														'<button class="btn btn-default wide_btn hide" type="button" id="wcc-btn-findCamera">检测摄像头</button>' +
														'<button class="btn btn-success wide_btn hide" type="button" id="wcc-btn-flashTakePhoto">拍照</button>' +
														'<button class="btn btn-default wide_btn hide" type="button" id="wcc-btn-back">后退</button>' +
														'<button class="btn btn-default wide_btn" type="button" id="wcc-btn-take_photo">拍照</button>' +
														'<span class="btn-file" id="wcc-btn-upload-span">' +
															'<a tabindex="-1" class="btn btn-success wide_btn">上传 <input tabindex="-1" size="40" title="上传" id="wcc-btn-upload_img" type="file" multiple="" name="userFile" onkeypress="return false;" onpaste="return false;" />' +
														'</span>' +
													'</div>' +
												'</div>' +
											'</div>' +
										'</div>' +
									'</div>' +
								'</div>' +
							'</div>',
		options: {
			"baseUrl": "",
			"url" : "file/upload",
            "flashUploadURL" : "file/flashUploadFile",
            "downloadURL": "file/downloadFile?fileName=",
            "flashURL" : "resources/js/takephoto.swf",
			"msgInvalidImg" : "合法图片格式包括jpeg，gif，png三种",
            "msgFileLimitSize" : "文件过大，请选择不超过5MB的图片上传",
            "msgUploadErr" : "文件上传失败，请重试",
            "cropCtrlTip" : "加载中。。。",
            "msgConfrimDeletePhoto" : "您确定要删除图片吗？",
            "lnkRemoveWrapperSelector" : "#wcc-lnk-cancel_span",
            "lnkRemoveLabel" : "删除",
            "loadIconSelector" : "#fileLoadImg",
			"changeLinkName": "图片上传",
			"msgDenyCamera":"我们没有在该机上找到网络摄像头。请安装摄像头并重试。"
		},
		
		init : function(options) {
			this.options = $.extend(this.options, options);
			if(this.options.baseUrl.lastIndexOf('/') !== (this.options.baseUrl.length - 1)) {
				this.options.baseUrl += '/';
			}

			this.url = this.options.baseUrl + this.options.url;
			this.flashUploadURL = this.options.baseUrl + this.options.flashUploadURL;
			this.$lnkUploadFile = $(this.options.lnkUploadFileSelector);
			this.flashAlternateContent = '<div id="takePhoto"><p>flash player 11.1及以上版本</p></div>';
			var $lnkRemoveFile = $(this.options.lnkRemoveFileSelector);
			this.$lnkUploadFile.bind("click", $.proxy(this.showUploadDialog, this));
			$lnkRemoveFile.bind("click", $.proxy(this.removeUserImage, this));
			this.changeLinkName = this.options.changeLinkName;
			this.downloadURL = this.options.baseUrl + this.options.downloadURL;
			this.defaultUserPhotoURL = (this.options.baseUrl + this.options.defaultUserPhotoURL);
			this.flashURL = (this.options.baseUrl + this.options.flashURL);
		},
		
		showUploadDialog : function(){
			var self = this;
			$('body > #file_upload_dialog').remove();
			
			var $tempContent = $(this.dlgTemplateString);
			
			var encryptFileName = $(this.options.userPhotoSelector).data('encryptFileName');
			if(encryptFileName) {
				$tempContent.find("#wcc-img-default_img").attr("src", this.downloadURL + encryptFileName + "&random=" + Math.random());
			} else {
				$tempContent.find("#wcc-img-default_img").attr("src", this.defaultUserPhotoURL);
			}
			
			$tempContent.find("#fileUploadlLabel").text(this.options.dlgTitle);
			var content = $tempContent.html();
			$('body').append(content);
			
			self.$load = $(self.options.loadIconSelector);
			self.$modalImg = $('.fileUploaderDialog #wcc-img-default_img');
			self.initFileUploadCtrl();
			$("#wcc-btn-cancel").bind("click", $.proxy(self.closeDlg,self));
			$("#wcc-btn-save").bind("click", $.proxy(self.cropAndSaveImage,self));
			$("#wcc-btn-take_photo").bind("click", $.proxy(self.initTakePhotoCtrl,self));
			$("#wcc-btn-back").bind("click", $.proxy(self.returnToImageUpload,self));
			$("#wcc-btn-findCamera").bind("click", $.proxy(self.reInitTakePhotoCtrl,self));
			$("#wcc-btn-flashTakePhoto").bind("click", $.proxy(self.takeFlashPhoto, self));
			$("#takePhotoWrapper").css("line-height","0px");
			$("#wcc-btn-take_photo").focus();
			
			$('#file_upload_dialog').on("hide.bs.modal",function(){
				if(self.flashCtrl){
					$("#takePhotoWrapper").addClass("hide");
				}
			});
			$('#file_upload_dialog').modal('show');
		}, 
	
		initFileUploadCtrl : function() {
			var self = this;
			$('.fileUploaderDialog #wcc-btn-upload_img').fileupload({
		        "url": self.url,
				"dataType" : 'json',
				"formData" : {
					"action" : "tempUpload"
				},
				done : function(e, data) {
					//self.$load.hide();
				    //self.$modalImg.removeClass('load');
					$('#wcc-btn-take_photo').removeClass('disabled');
					$('#wcc-btn-upload-span > a').removeClass('disabled');
					
					if (typeof data.result === "object") {
						if (data.result.status === "success") {
							self.startImageCrop(self.downloadURL, data.result.value);
						} else {
							if (data.result.value === "invalidImg") {
								self.showError(self.options.msgInvalidImg);
							} else if (data.result.value === "exceedFileLimitSize") {
								self.showError(self.options.msgFileLimitSize);
							} else {
								self.showError(self.options.msgUploadErr);
							}
						}
					}
				},
				change : function() {
					//self.$modalImg.addClass('load');
					//self.$load.show();
					$('#wcc-btn-take_photo').addClass('disabled');
					$('#wcc-btn-upload-span > a').addClass('disabled');
				},
				fail : function() {
					if(arguments[1] && arguments[1].textStatus === "parsererror"){ // the response is HTML not JSON,  means session time out
						self.closeDlg(); 
						window.location.reload();
					}else{
						//self.$modalImg.removeClass('load');
						//self.$load.hide();
						$('#wcc-btn-take_photo').removeClass('disabled');
						$('#wcc-btn-upload-span > a').removeClass('disabled');
						
						self.showError(self.options.msgUploadErr);
					}
				}
			});
		},
		
		startImageCrop : function(downloadURL, fileName){
			var self = this;
			
			$("#wcc-btn-take_photo").addClass("hide");
			$("#wcc-btn-upload-span").addClass("hide");
			$("#wcc-btn-save").removeClass("hide");
			$("#wcc-btn-cancel").removeClass("hide");
			$("#wcc-btn-save").addClass('disabled');
			$("#wcc-btn-cancel").addClass('disabled');
			
			self.$modalImg.attr('src', downloadURL+fileName);
			self.$modalImg.attr('fileName', fileName);
			self.$modalImg.load(function() {
				$("#photoTip1").addClass("hide");
				$("#photoTip2").removeClass("hide");
				$("#photoTip3").addClass("hide");
				
				$("#wcc-btn-save").removeClass('disabled').focus();
				$("#wcc-btn-cancel").removeClass('disabled');
				self.hideError();
				//self.dialog.view.find('.btn-success').removeClass('disabled');
				self.initCropAndSlider();
			});
		},
		
		initTakePhotoCtrl : function(){
			var self = this;
			self.$modalImg.parent().addClass("hide");
			$("#inline_error_container").addClass("hide");
			$("#takePhotoWrapper").removeClass("hide").html(self.flashAlternateContent);
			var osAndAgent = navigator.appVersion;
			var isSafariUnderMAC = (osAndAgent.indexOf("Mac OS") != -1 && osAndAgent.indexOf("Safari") != -1 && osAndAgent.indexOf("Chrome") == -1);
			var flashvars = {
				"logFuncName" : "ImageUploader.flashLog",
				"callbackName" : "ImageUploader.flashEventHandler",
				"allowScriptAccess" : "always",
				"isSafariUnderMAC" : isSafariUnderMAC ? "true" : "false"
			};
			var params = {};
			var attributes = {};
			swfobject.embedSWF(self.flashURL, "takePhoto", "240", "160", "11.1.0",false,
							flashvars, params, attributes, function(e){
								if(e.success){
									self.flashCtrl = e.ref;
									$("#wcc-txt-text").addClass("hide");
									$("#wcc-btn-upload-span").addClass("hide");
									$("#wcc-btn-take_photo").addClass("hide");
									$("#wcc-btn-findCamera").attr("disabled","disabled").removeClass("hide");
									$("#wcc-btn-flashTakePhoto").attr("disabled","disabled").removeClass("hide");
									$("#wcc-btn-back").removeClass("hide");
									
								}else{
									// to do :  show inline error here....
									$("#takePhotoWrapper").addClass("hide");
									$("#wcc-txt-text").removeClass("hide");
									$("#photoTip1").addClass("hide");
									$("#photoTip2").addClass("hide");
									$("#photoTip3").removeClass("hide");
									$("#wcc-btn-upload-span").addClass("hide");
									$("#wcc-btn-take_photo").addClass("hide");
									$("#wcc-btn-back").removeClass("hide");
								}
								$("#wcc-btn-back").focus();
							});
			
		},
		
		reInitTakePhotoCtrl : function(){
			$("#takePhotoWrapper").html("");
			this.initTakePhotoCtrl();
		},
		
		returnToImageUpload : function(){
			//destroy the flash
			$("#takePhotoWrapper").html("").addClass("hide");
			this.flashCtrl = null;
			this.$modalImg.parent().removeClass("hide");
			$("#wcc-txt-text").removeClass("hide");
			$("#photoTip1").removeClass("hide");
			$("#photoTip2").addClass("hide");
			$("#photoTip3").addClass("hide");
			$("#wcc-btn-upload-span").removeClass("hide");
			$("#wcc-btn-take_photo").removeClass("hide");
			$("#wcc-btn-findCamera").addClass("hide");
			$("#wcc-btn-flashTakePhoto").addClass("hide");
			$("#wcc-btn-back").addClass("hide");
			$("#wcc-btn-take_photo").focus();
		},
		
		flashLog : function(message){
			if(window.console !== undefined){
				console.log(message);
			}
		},
		
		closeFlashAndStartCrop : function(fileName, downloadURL){
			$("#takePhotoWrapper").html("").addClass("hide");
			this.flashCtrl = null;
			this.$modalImg.parent().removeClass("hide");
			$("#wcc-btn-findCamera").addClass("hide");
			$("#wcc-btn-flashTakePhoto").addClass("hide");
			$("#wcc-btn-back").addClass("hide");
			$("#wcc-txt-text").removeClass("hide");
			this.startImageCrop(downloadURL, fileName);
		},
		
		flashEventHandler : function(eventName,eventData){
			var self = this;
			if(window.console !== undefined){
				console.log(eventName + ":" + eventData);
			}
			if(eventName =="onCamDeny" || eventName == "onCamNotFound"){
				$("#wcc-btn-findCamera").removeAttr("disabled");
				self.flashCtrl.callflash("setErrMsg", self.options.msgDenyCamera);
				if (eventName == "onCamNotFound") {
					self.showError(self.options.msgDenyCamera);
				}
			} else if(eventName == "onCamReady"){
				$("#wcc-btn-flashTakePhoto").removeAttr("disabled");
				$("#wcc-btn-findCamera").attr("disabled","disabled");
				self.flashCtrl.callflash("setOption", "uploadURL", self.flashUploadURL);
			}else if(eventName == "onUploadComplete"){
				// get image url and go to image crop ctrl.
				if(eventData){
					var result = JSON.parse(eventData);  // to do : IE7 cannot support JSON.
					if(result.status == "success"){
						self.closeFlashAndStartCrop(result.value, self.downloadURL);
					}else{
						self.showError(self.options.msgUploadErr);
					}
				}
			}
		
		},
		
		takeFlashPhoto : function(){
			if(this.flashCtrl){
				this.flashCtrl.callflash("takePhoto");
			}
		},
		
		closeDlg : function(){
			$('#file_upload_dialog').modal('hide');
		},
		
		showError : function(message){
			$("#wcc-txt-inline_error span").text(message);
			$("#inline_error_container").removeClass("hide");
			$('#wcc-txt-text').addClass('hide');
		},
		
		hideError : function(){
			$("#wcc-txt-inline_error span").text("");
			$("#inline_error_container").addClass("hide");
			$('#wcc-txt-text').removeClass('hide');
		},
		
		initCropAndSlider : function(){
			var self = this;
			var notifySliderWhenZoom = function(){
				$sliderTarget.trigger("crop:changed", {"percent" : this.percent});
			};
			self.$modalImg.crop({
				width:160,
				height:160,
				loading: this.options.cropCtrlTip,
				slideControls: true,
				zoomIn : notifySliderWhenZoom,
				zoomOut : notifySliderWhenZoom
			}).on ( 'crop', function( event ) {
				self.cropOpt = {
					x : event.cropX,
					y : event.cropY,
					width : event.cropW,
					height : event.cropH
				}
			});
			// add round corner class for Jquery crop controller
			$(".cropFrame").addClass("t-radius6");
			$(".cropFrame").css('margin', 'auto');
			$(".cropLoading").addClass("hide");
			var cropCtrl = self.$modalImg.data("crop");
			/*
			var browserInfo = Train.Util.browser;
			if(browserInfo.isIE7 || (browserInfo.msie && browserInfo.version == "8.0") ){
			// add icon text for IE7/8
				$("a.cropZoomIn").text("+");
				$("a.cropZoomOut").text("-");
			}
			*/
			cropCtrl.zoom(cropCtrl.minPercent);
			var sliderMin = cropCtrl.getMinPercent();
			var sliderMax = 1;
			var sliderStep = cropCtrl.getStepValue();
			var sliderRange = [sliderMin,sliderMax];
			var $sliderTarget = $("#imgCropSlide");
			if($sliderTarget.length > 0 && cropCtrl.minPercent < 1){
				$sliderTarget.simpleSlider({
					"range" : sliderRange,
					"step"	: sliderStep
				});
			}
			$sliderTarget.on("slider:changed",function(event, data){
				if(data.trigger != "setValue"){
					cropCtrl.zoom(data.value);
				}
			}).on('crop:changed', function(event,data){
				if(this.value != data.percent){
					$(this).simpleSlider("setValue",data.percent);
				}
			});
		
		
		},
		
		removeUserImage : function() {
			var self = this;
			if(window.confirm(self.options.msgConfrimDeletePhoto)){
				$.ajax({
					type: 'POST',
	        		url: self.url,
	        		cache:false,
	        		data:{
	        			"action":"delete"
	        		},
	        		success: function(data){
						$userPhoto = $(self.options.userPhotoSelector);
	        			$userPhoto.attr('src', null);
	    				$userPhoto.attr('src', self.defaultUserPhotoURL);
						$(self.options.lnkRemoveWrapperSelector).html(self.options.lnkRemoveLabel);
	        		},
	        		fail: function(){
	        			window.location.reload();
	        		}
	        	});
			}
		},
		
		cropAndSaveImage : function(){
			var self = this;
			self.$modalImg.addClass('load');
			var fileName = self.$modalImg.attr("fileName");
			self.$load.show();
			$("#wcc-btn-save").addClass("disabled");
			$.ajax({
				type: 'POST',
        		url: self.url,
        		cache:false,
        		data:{
        			"cropStartX": self.cropOpt.x,
        			"cropStartY": self.cropOpt.y,
        			"cropWidth":  self.cropOpt.width,
        			"cropHeight": self.cropOpt.height,
        			"action":"cropAndSave",
					"fileName": fileName
        		},
        		success: function(data){
        			data = $.parseJSON(data);
        			if (data.status === "success"){
						$userPhoto = $(self.options.userPhotoSelector);
        				//$userPhoto.attr('src', null);
        				$(self.options.lnkRemoveWrapperSelector).html("<a id='wcc-lnk-remove' href='javascript: void(0)'>" + self.options.lnkRemoveLabel + "</a>");
        				$(self.options.lnkRemoveFileSelector).bind("click", $.proxy(self.removeUserImage,self));
        				$(self.options.lnkUploadFileSelector).attr('title', self.options.changeLinkName);
        				$(self.options.lnkUploadFileSelector).text(self.options.changeLinkName);
						$userPhoto.attr('src', self.downloadURL + data.value + "&random=" + Math.random());
						$userPhoto.data('encryptFileName', data.value);
						self.closeDlg(); 
						/*
        				window.setTimeout(function(){
							$userPhoto.attr('src', data.value);
							self.closeDlg();  // downgrade the possibility of image cotent is not latest issue
							},500);  
						$.ajax({
							url : data.value,
						    headers : {"Cache-Control" : "max-age=0", "Accept": "image/*"},
							success : function(imageData){
								$userPhoto.attr('src', data.value);
								self.closeDlg(); 
							}
						});	 */
        			}else{
        				self.$modalImg.removeClass('load');
        				self.$load.hide();
        				$("#wcc-btn-save").removeClass("disabled");
        				self.showError();
        			}
        			
        		},
        		fail: function(){
        			window.location.reload(self.options.msgUploadErr);
        		}
        	});
		}
	}
