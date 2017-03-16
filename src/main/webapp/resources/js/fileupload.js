;(function($, _) {
	var defaultOptions = {
		'fileDownloadUrl': 'hagkFile/download?fileName=',
		'fileTempUploadUrl': 'hagkFile/tempUpload',
		'fileUploadUrl': 'hagkFile/upload',
		'swfFileUrl': 'resources/js/common/Moxie.swf',
		'xapFileUrl': 'resources/js/Moxie.xap',
		'dialogTitle': '上传头像',
		'defaultImageUrl': 'resources/images/user_avatar.png',
		'maxFileSize': '5mb',
		'validFileExtensions': 'jpg,jpeg,png,gif'
	};
	
	var fileUploadDialogTemplate = '<div class="modal fade" id="file-upload-modal" tabindex="-1" role="dialog" aria-labelledby="upload-file-label">' +
										'<div class="modal-dialog" role="document" style="width:300px !important">' +
											'<div class="modal-content">' +
												'<div class="modal-header">' +
													'<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
													'<h4 class="modal-title" id="upload-file-label">{{ dialogTitle }}</h4>' +
												'</div>' +
												'<div class="modal-body">' +
													'<div id="file-upload-img-area" class="text-center">' +
														'<img id="file-upload-img" style="border: 1px solid #cecece" width="160" height="160" src="{{ imageUrl }}"/>' +
													'</div>' +
													'<div id="file-upload-btn-area" class="text-center" style="margin-top: 20px;">' +
														'<button id="file-upload-temp-upload-btn" class="btn btn-success" style="width: 100%;">上传</button>' +
														'<button id="file-upload-normal-upload-btn" class="btn btn-success" style="width: 100%; display:none;">保存</button>' +
														'<button id="file-upload-cancel-btn" class="btn btn-normal" style="width: 100%; margin-top: 10px;">取消</button>' +
													'</div>' +
												'</div>' +
											'</div>' +
										'</div>' +
								   '</div>';
	
	_.templateSettings = {
		interpolate: /\{\{(.+?)\}\}/g
	};
	
	var fileUploadDialogCompiled = _.template(fileUploadDialogTemplate);
	
	function HagkUpload(options) {
		this.options = options;
	}
	
	HagkUpload.prototype.init = function() {
		this.options = $.extend(this.options, defaultOptions);
		
		if(!this.options.baseUrl || !this.options.uploadTriggerSelector || !this.options.imageSelector) {
			alert('baseUrl | uploadTriggerSelector | imageSelector parameters must be set');
			return;
		}
		
		// make sure baseUrl is ended with '/'
		var baseUrl = this.options.baseUrl;
		if(baseUrl.lastIndexOf('/') !== (baseUrl.length - 1)) {
			baseUrl += '/';
		}
		this.fileDownloadUrl = baseUrl + this.options.fileDownloadUrl;
		this.fileTempUploadUrl = baseUrl + this.options.fileTempUploadUrl;
		this.fileUploadUrl = baseUrl + this.options.fileUploadUrl;
		this.defaultImageUrl = baseUrl + this.options.defaultImageUrl;
		this.swfFileUrl = baseUrl + this.options.swfFileUrl;
		this.xapFileUrl = baseUrl + this.options.xapFileUrl;
		this.$uploadTrigger = $(this.options.uploadTriggerSelector);
		this.$image = $(this.options.imageSelector);
		this.cropOpt = {};
		
		// bind events
		$(this.options.uploadTriggerSelector).on('click', $.proxy(this._openUploadDialog, this));
	};
	
	HagkUpload.prototype._openUploadDialog = function() {
		var initData = { 'dialogTitle': this.options.dialogTitle };
		var encryptFileName = this.$image.data('encryptFileName');
		var initImageUrl = encryptFileName ? (this.fileDownloadUrl + encryptFileName + '&random=' + Math.random()) : this.defaultImageUrl;
		initData['imageUrl'] = initImageUrl;
		
		var fileUploadDialogString = fileUploadDialogCompiled(initData);
		$('#file-upload-modal').remove();
		$('body').append(fileUploadDialogString);
		
		this._initFileUploadWidget();
		$('#file-upload-normal-upload-btn').on('click', $.proxy(this._onRealUploadBtnClick, this));
		$('#file-upload-cancel-btn').on('click', $.proxy(this._closeUploadDialog, this));
		
		this.$dialog = $('#file-upload-modal');
		this.$dialogImage = $('#file-upload-img');
		this.$saveBtn = $('#file-upload-normal-upload-btn');
		this.$tempUploadBtn = $('#file-upload-temp-upload-btn');
		
		this.$dialog.modal('show');
	};
	
	HagkUpload.prototype._closeUploadDialog = function() {
		this.$dialog.modal('hide');
		
		if(this.fileUploadWidget) {
			this.fileUploadWidget.destroy();
		}
	};
	
	HagkUpload.prototype._initFileUploadWidget = function() {
		var self = this;
		
		if(this.fileUploadWidget) {
			this.fileUploadWidget.destroy();
		}

		this.fileUploadWidget = new plupload.Uploader({
			browse_button : 'file-upload-temp-upload-btn',
			url: this.fileTempUploadUrl,
			file_data_name: 'uploadedFile',
			multi_selection: false,
			flash_swf_url: this.swfFileUrl,
			silverlight_xap_url: this.xapFileUrl,
			multipart_params: {},
			filters: {
				max_file_size: this.options.maxFileSize,
				mime_types: [
					{title: 'Document Files', extensions: this.options.validFileExtensions}
				]
			},
			init: {
				FilesAdded: function(uploader, files) {
					self.fileUploadWidget.disableBrowse(true);
					self.fileUploadWidget.start();
				},
				FileUploaded: function(uploader, file, responseObject) {
					var encryptedFileName = responseObject.response;
					
					if(!encryptedFileName) {
						alert('图片上传异常');
						return;
					}
					
					self.$dialogImage.attr('src', self.fileDownloadUrl + encryptedFileName);
					self.$dialogImage.attr('fileName', encryptedFileName);
					self.$dialogImage.load(function() {
						self._initCropAndSlider();
					});
					
					self.$tempUploadBtn.hide();
					self.$saveBtn.show();
					
					self.fileUploadWidget.destroy();
					self.fileUploadWidget = null;
				},
				UploadProgress: function(uploader, file) {
					//
				},
				Error: function(uploader, errObject) {
					if(errObject.code === plupload.FILE_SIZE_ERROR) {
						alert('文件大小超过最大上传文件大小');
					} else if(errObject.code === plupload.FILE_SIZE_ERROR) {
						alert('文件格式不合法');
					}
				}
			}
		});
		
		this.fileUploadWidget.init();
	};
	
	HagkUpload.prototype._initCropAndSlider = function() {
		var self = this;
		
		var notifySliderWhenZoom = function(){
			$sliderTarget.trigger("crop:changed", {"percent" : this.percent});
		};
		
		this.$dialogImage.crop({
			width: 160,
			height: 160,
			loading: '加载中。。。',
			slideControls: true,
			zoomIn : notifySliderWhenZoom,
			zoomOut : notifySliderWhenZoom
		}).on ('crop', function(event) {
			self.cropOpt = {
				x: event.cropX,
				y: event.cropY,
				width: event.cropW,
				height: event.cropH
			};
		});
		
		// add round corner class for Jquery crop controller
		$(".cropFrame").css('margin', 'auto');
		$(".cropLoading").addClass("hide");
		
		var cropCtrl = this.$dialogImage.data("crop");
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
	};
	
	HagkUpload.prototype._onRealUploadBtnClick = function() {
		var self = this;
		
		var data = {
			'cropStartX': this.cropOpt.x,
			'cropStartY': this.cropOpt.y,
			'cropWidth':  this.cropOpt.width,
			'cropHeight': this.cropOpt.height,
			'fileName': this.$dialogImage.attr('fileName'),
		}
		
		$.ajax({
			type : 'post',
			url : this.fileUploadUrl,
			data: JSON.stringify(data),
			cache: false,
			contentType: 'application/json',
			success : function(data) {
				self.$image.attr('src', self.fileDownloadUrl + data);
				self.$image.data('encryptFileName', data);
				self._closeUploadDialog();
			},
			error: function() {
				alert("图片上传失败");
			}
		});
	};
	
	window.HagkUpload = HagkUpload;
})(jQuery, _);