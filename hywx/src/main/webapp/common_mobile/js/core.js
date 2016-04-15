var WQ = {
	/**
	 * json to string
	 */
	obj2str: function(o) {
		var r = [];
		if (typeof o == "string")
			return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
		if (typeof o == "object") {
			if (!o.sort) {
				for ( var i in o)
					r.push(i + ":" + WQ.obj2str(o[i]));
				if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
					r.push("toString:" + o.toString.toString());
				}
				r = "{" + r.join() + "}"
			} else {
				for ( var i = 0; i < o.length; i++) {
					r.push(WQ.obj2str(o[i]));
				}
				r = "[" + r.join() + "]"
			}
			return r;
		}
		return o.toString();
	},
	/**
	 * string to json
	 */
	jsonEval: function(data) {
		try {
			if ($.type(data) == 'string')
				return eval('(' + data + ')');
			else
				return data;
		} catch (e) {
			return {};
		}
	},
	/**
	 * 全局ajax错误处理
	 */
	ajaxError : function(xhr, ajaxOptions, thrownError) {
		$('.ui-container').html('<section class="ui-notice"><i></i><p>服务器忙，请稍后重试！</p></section>');
	},
	/**
	 * 从userAgent中取auth-code
	 */
	authCode: function() {
		var ua = navigator.userAgent;
		if (ua){
			if ((m = ua.match(/WqAc\/([\w.]*)/)) && m[1]) {
				return m[1];
			}
		}
		return '';
	}
};

/**
 * TODO:获取URL参数
 */
function request(param) {
	var oRegex = new RegExp('[\?&]' + param + '=([^&]+)', 'i');
	var oMatch = oRegex.exec(window.location.search);
	if (oMatch && oMatch.length > 1)
		return oMatch[1];
	else
		return '';
}

/**
 * TODO:获取头像
 */
template.helper('getFace', function (face, name, id) {
	if (face && face.indexOf('default-face-small.png') == -1) {
		return '<img class="face" src="' + face + '"/>';
	} else {
		return '<span class="face">' + (name ? name.substr(0, 1) : '') + '</span>';
	}
});