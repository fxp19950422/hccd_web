$(function(){  
    $.ajaxSetup({  
        contentType: "application/x-www-form-urlencoded;charset=utf-8",  
        cache: false,  
        complete: function(XHR, TS){  
            var resText = XHR.responseText;  
            var sessionstatus = XHR.getResponseHeader("sessionstatus");  
            var loginPath = XHR.getResponseHeader("loginPath");  
            if (401 == XHR.status && "timeout" == sessionstatus) {   
                // 也可以使用下面的原生js的确认框，如果确认则跳转  
               alert('您的会话已经过期，请重新登陆后继续操作！');
               window.location.replace(loginPath);  
                
                return;  
            }  
        }  
    });  
});  

$(function(){
	$.blockUI.defaults.css = {
    		
		 	padding:        0, 
	        margin:         0, 
	        width:          '30%', 
	        top:            '40%', 
	        left:           '35%', 
	        textAlign:      'center', 
	        cursor:         'wait', 
            border: 'none', 
            padding: '15px', 
            backgroundColor: '#111', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            opacity: .5, 
            color: '#fff' }; 
	$.blockUI.defaults.message='<h1>请稍后，正在加载中...</h1>';
});


var sa = {  
        ajax : function(options) {  
            //save the old success callback 
            var _beforeSend = options.beforeSend;  
            var _complete = options.complete; 
            options.beforeSend = function() { 
            	$.blockUI(); 
            	if (_beforeSend){
            		_beforeSend();  
            	} 
            };  
            options.complete = function() {  
            	$.unblockUI();  
            	if (_complete){
            		_complete(); 
            	}
               
            };  
            
            $.ajax(options);  
        }  
    };


jQuery.fn.loadAngular = function( url, params, callback ) {
	
	if ( typeof url !== "string" && _load ) {
		
		return _load.apply( this, arguments );
	}

	var selector, response, type,
		self = this,
		off = url.indexOf(" ");

	if ( off >= 0 ) {
		selector = url.slice( off, url.length );
		url = url.slice( 0, off );
	}

	// If it's a function
	if ( jQuery.isFunction( params ) ) {

		// We assume that it's the callback
		callback = params;
		params = undefined;

	// Otherwise, build a param string
	} else if ( params && typeof params === "object" ) {
		type = "POST";
	}

	// If we have elements to modify, make the request
	if ( self.length > 0 ) {
		$.ajax({
			url: url,

			// if "type" variable is undefined, then "GET" method will be used
			type: type,
			dataType: "html",
			data: params
		}).done(function( responseText ) {

			// Save response for use in complete callback
			response = arguments;

			AngularHelper.Compile(self, responseText);
//			self.html( selector ?
//
//				// If a selector was specified, locate the right elements in a dummy div
//				// Exclude scripts to avoid IE 'Permission Denied' errors
//				jQuery("<div>").append( jQuery.parseHTML( responseText ) ).find( selector ) :
//
//				// Otherwise use the full result
//				responseText );

		}).fail(function(response){
			AngularHelper.Compile(self, response.responseText);
		}).complete( callback && function( jqXHR, status ) {
			self.each( callback, response || [ jqXHR.responseText, status, jqXHR ] );
		});
	}

	return this;
};


//摘要:将指定字符串中的格式项替换为指定数组中相应对象的字符串表示形式。
//参数:复合格式字符串。
//返回结果:format的副本，其中的格式项已替换为 args 中相应对象的字符串表示形式。
//应用实例:var str = String.format("S{0}T{1}Twt", "n", "e"); //结果：SnTeTwt
String.format = function () {
  var c = arguments[0], b = arguments[1];
  if (b instanceof Array) {
      for (var a = 0; a < b.length; a++) {
          var b = new RegExp("\\{" + a + "\\}", "gm");
          c = c.replace(b, b[a]);
      }
  } else {
      for (var a = 0; a < arguments.length - 1; a++) {
          var b = new RegExp("\\{" + a + "\\}", "gm");
          c = c.replace(b, arguments[a + 1]);
      }
  }
  return c;
}

//摘要:返回字符串字节长度，区分中英文
//参数:将要计算的字符串
//返回结果：字符串实际的字符长字
//应用实例：String.charLength("Sn杨");结果：4
String.charLength = function (c) {
  c = c || '';
  var b = 0;
  for (var a = 0; a < c.length; a++) {
      if ((c.charCodeAt(a) >= 0) && (c.charCodeAt(a) <= 255)) {
          b++
      } else {
          b += 2
      }
  }
  return b
}

//摘要:在此实例的结尾追加指定的32位无符号整数的字符串表示形式。
//参数:要追加的值。
//返回结果:val，要追加的字符串。
//应用实例:var str = new StringBuffer("Sn").append("TeTwt");//结果：SnTeTwt
function StringBuilder(val) {
  this._strings = new Array();
  this.append(val);
}
StringBuilder.prototype.append = function (val) {
  this._strings.push(val);
  return this;
}
StringBuilder.prototype.toString = function () {
  var a = (arguments.length == 0) ? "" : arguments[0];
  return this._strings.join(a);
}

function numberFormat() { }
//摘要:金钱千位分隔符
//参数:用于分隔的数字
//返回结果:以","分隔和字符串
//应用实例:var nf = new numberFormat().commafy(123456);//结果：123,456
numberFormat.prototype.commafy = function (num) {
  num = num + "";
  var re = /(-?\d+)(\d{3})/;
  while (re.test(num)) {
      num = num.replace(re, "$1,$2");
  }
  return num;
}
//摘要:还原numberFormat()方法
//返回结果:返回浮点数
//应用实例:var nf = new numberFormat().commafyBack("123,456");//结果：123456
numberFormat.prototype.commafyBack = function (num) {
  var x = num.split(',');
  return parseFloat(x.join(""));
}
//摘要:加法函数，用来得到精确的加法结果
//返回结果：arg1加上arg2的精确结果
//应用实例：new numberFormat().add(10.1,10.2); //结果：20.3
numberFormat.prototype.add = function (arg1, arg2) {
  var r1, r2, m;
  try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
  try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }
  m = Math.pow(10, Math.max(r1, r2));
  return (arg1 * m + arg2 * m) / m;
}
//摘要:减法函数，用来得到精确的加法结果
//返回结果：arg1减上arg2的精确结果
//应用实例：new numberFormat().add(10.1,10.2); //结果：-0.1
numberFormat.prototype.sub = function (arg1, arg2) {
  var r1, r2, m;
  try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
  try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }
  m = Math.pow(10, Math.max(r1, r2));
  return (arg1 * m - arg2 * m) / m;
}

//摘要:返回一个新字符串，该字符串通过在此实例中的字符左侧填充指定的 Unicode 字符来达到指定的总长度，从而使这些字符右对齐。
//参数:
//t(totalWidth):结果字符串中的字符数，等于原始字符数加上任何其他填充字符。
//p(paddingChar):Unicode 填充字符。
//返回结果:与此实例等效的一个新字符串
//应用实例：var str = "1".leftpad(2, "0");结果：01 
//左填充
String.prototype.padLeft = function (t, p) {
  var e = []; p = p || "0";
  for (var d = 0, a = t - this.length; d < a; d++) {
      e.push(p);
  }
  e.push(this).j;
  return e.join('');
}
//右填充
String.prototype.padRight = function (t, p) {
  var e = [this]; p = p || "0";
  for (var d = 0, a = t - this.length; d < a; d++) {
      e.push(p);
  }
  return e.join('');
}

//摘要:用来打乱一维数组并返回随机数
//参数：
//pool:     指定随机数的数值范围
//min:      最小数值
//max:      最大数值
//count:    返回随机数的个数
//padding:  填充数
//sort:     排序
//应用实例：Math.shuffle({ pool: "123456789", min: 1, max: 9, count: 5 }); 结果：3,6,6,5,1
Math.shuffle = function (h) {
  function extend(s, t) {
      if (!s) {
          return {};
      } if (!s) {
          return s;
      }
      var o = {};
      for (var i in s) {
          o[i] = typeof (t[i]) == 'undefined' ? s[i] : t[i];
      }
      return o;
  }
  var g = extend({
      pool: undefined, //指定随机数的数值范围
      min: 1,          //最小数值
      max: 10,         //最大数值
      count: 5,        //返回随机数的个数
      padding: 0,      //填充数
      sort: false      //排序
  }, h);
  if (g.max < g.count) {
      return [];
  }
  var f;
  if (g.pool) {
      f = g.pool;
  } else {
      f = [];
      for (var e = g.min; e <= g.max; e++) {
          f.push(e);
      }
  }
  var b = [], e = 0;
  while (e < g.count) {
      var c = parseInt((f.length - e) * Math.random(), 10);
      if (g.padding > 0) {
          b.push(f[c].toString().leftpad(g.padding));
      } else {
          b.push(f[c]);
      }
      e++;
      var d = f[c];
      f[c] = f[f.length - e];
      f[f.length - e] = d;
  }
  if (g.sort) {
      b = b.sort(function (j, i) {
          return parseInt(j, 10) - parseInt(i, 10)
      })
  }
  return b;
}

//摘要:对象与数组的转换
//参数：对象字面量
//应用实例： var ar = objectToArray({ a: "a",b: "b"});   如：ar[1]，结果：b
var objectToArray = function (b) {
  if (!b) {
      return [];
  }
  var a = [];
  for (m in b) {
      if (!!b[m]) {
          a.push(b[m]);
      }
  }
  return a;
}

//摘要:获取和设置当前url查询字符串变量集合，#。
//参数:查询字符串的名称
//返回结果:查询字符串的名称对应的值
//应用实例:/show.html?id=1#page=2
//获取：var a = Request.QueryHaseString.get("page");结果：2
//设置：Request.QueryHaseString.set({ page: 2 });
; (function (l) {
  var a = function () {
      var d, e = {}, b;
      d = window.location.hash.replace(/^#/, "");
      if (d == "") {
          this.uhash = {}
      } else {
          d = d.split("|");
          for (var c = 0; c < d.length; c++) {
              b = d[c].split("=");
              e[b[0]] = b[1]
          }
          this.uhash = e
      }
  };
  a.prototype.set = function (e) {
      var d = [];
      if (arguments.length == 1 && /Object/.test(Object.prototype.toString.call(e))) {
          for (var c in e) {
              if (e.hasOwnProperty(c)) {
                  this.uhash[c] = e[c]
              }
          }
          for (var b in this.uhash) {
              d.push(b + "=" + this.uhash[b])
          }
          window.location.hash = d.join("|")
      } else {
          logger.info("设置hash的参数不正确")
      }
  };
  a.prototype.get = function (b) {
      if (arguments.length == 1) {
          return this.uhash[b]
      } else {
          return this.uhash
      }
  };
  l.QueryHaseString = new a()
})(window.Request = window.Request || {})

//摘要:获取当前url参数值
//应用实例：/show.html?a=2
//var c = new Request.URLParser(window.location.href);
//var a = new Request.QueryString(c.getQueryString());
//var b = a.get("a"); //结果：2
var Request = {}
Request.URLParser = function (a) {
  this._fields = {
      Username: 4,
      Password: 5,
      Port: 7,
      Protocol: 2,
      Host: 6,
      Path: 8,
      URL: 0,
      QueryString: 9,
      Fragment: 10
  };
  this._values = {};
  this._regex = null;
  this.version = 0.1;
  this._regex = /^((\w+):\/\/)?((\w+):?(\w+)?@)?([^\/\?:]+):?(\d+)?(\/?[^\?#]+)?\??([^#]+)?#?(\w*)/;
  for (var b in this._fields) {
      this["get" + b] = this._makeGetter(b)
  }
  if (typeof a != "undefined") {
      this._parse(a)
  }
}
Request.URLParser.prototype.setURL = function (a) {
  this._parse(a)
}
Request.URLParser.prototype._initValues = function () {
  for (var a in this._fields) {
      this._values[a] = ""
  }
}
Request.URLParser.prototype._parse = function (a) {
  this._initValues();
  var b = this._regex.exec(a);
  if (!b) {
      throw "DPURLParser::_parse -> Invalid URL"
  }
  for (var c in this._fields) {
      if (typeof b[this._fields[c]] != "undefined") {
          this._values[c] = b[this._fields[c]]
      }
  }
}
Request.URLParser.prototype._makeGetter = function (a) {
  return function () {
      return this._values[a]
  }
}
Request.QueryString = function (d) {
  var c = d.split("&");
  this.params = {};
  for (var b = 0,	a = c.length; b < a; b++) {
      var e = c[b].split("=");
      if (e.length != 2) {
          continue
      }
      this.params[e[0]] = e[1]
  }
}
Request.QueryString.prototype.get = function (a) {
  return this.params[a]
}





function getDateId(date){
	var month = String(add(date.getMonth(), 1));
	var day = String(date.getDate());
	var monthStr = month.length == 1 ? "0" + month : month;
	var dayStr = day.length == 1 ? "0" + day : day;
	var dateId = date.getFullYear()+""+monthStr+""+dayStr;
	return dateId;
}

function removeBoFromPage() {
	if($("#hiddenUtrainingBO")){
		$("#hiddenUtrainingBO").removeData("utrainingBO");
	}
}


function getBoFromPage(){
	return $("#hiddenUtrainingBO").data("utrainingBO");
}

function putBo2Page(obj){
	if($("#hiddenUtrainingBO")){
		return $("#hiddenUtrainingBO").data("utrainingBO",obj);
	}
}

function getInfoFromExt(list,key){
	if(list){
		for(var j=0;j<list.length;j++){
			var obj = list[j];
			if(obj["itemName"]&&obj["itemName"]==key){
				return obj["itemValue"];
			}
		}
	}
}

Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function getPlayerName(list,id){
	if(list){
		for(var k=0;k<list.length;k++){
			var obj = list[k];
			if(obj.id==id){
				return obj.name;
			}
		}
	}
}

function isInPlayerList(list,id){
	if(list){
		for(var k=0;k<list.length;k++){
			var obj = list[k];
			if(obj.id==id){
				return true;
			}
		}
	}
	false;
}

function add(a, b) {
	return parseInt(a) + parseInt(b);
}