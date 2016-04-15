var drawRoundTimerSeconds = null;
var drawRoundTimerFinish = null;
var drawRoundArr = null;
var drawRoundTimer = null;

var curDataStr;

var ajaxError = '服务器忙，请稍后重试！';

/**
 * TODO:ajax设置全局错误处理
 */
/*
 * $(function(){ $.ajaxSetup({ error: function(jqXHR, textStatus, errorThrown){
 * $('.ui-container').html('<section class="ui-notice"><i></i><p>' +
 * ajaxError + '</p></section>'); } }); });
 */

/**
 * TODO:画圆
 */
function drawTimer(id, percent) {
	$('#round_' + id).html(
			'<div class="round_txt"></div><div class="round_bg'
					+ (percent > 5 ? ' gt50"' : '"')
					+ '><div class="pie"></div>'
					+ (percent > 5 ? '<div class="pie fill"></div>' : '')
					+ '</div>');
	var deg = 360 / 10 * percent;
	$('#round_' + id + ' .round_bg .pie').css({
		'-moz-transform' : 'rotate(' + deg + 'deg)',
		'-webkit-transform' : 'rotate(' + deg + 'deg)',
		'-o-transform' : 'rotate(' + deg + 'deg)',
		'transform' : 'rotate(' + deg + 'deg)'
	});
	$('#round_' + id + ' .round_txt').html(parseInt(percent * 10) + '%')
}

/**
 * TODO:画圆--计算值
 */
function stopNote(id, dir) {
	var seconds = (drawRoundTimerFinish - (new Date().getTime())) / 100;
	var percent = 100 - ((seconds / drawRoundTimerSeconds) * 10);
	percent = Math.floor(percent * 100) / 100;
	if (percent <= dir) {
		drawTimer(id, percent);
	} else {
		console.log(1);
		var __dir = $('#round_' + id).data('dir');
		$('#round_' + id + ' .round_txt').text(__dir + '%');
		window.clearInterval(drawRoundTimer);
	}
}



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
 * TODO:JSON模版替换
 */
function formatTemplate(data, tmpl) {
	var format = {
		name : function(x) {
			return x
		}
	};
	return tmpl.replace(/{(\w+)}/g, function(m1, m2) {
		if (!m2)
			return "";
		return (format && format[m2]) ? format[m2](data[m2]) : data[m2];
	});
}


/**
 * TODO:百分比计算值
 */
function getPercentNum(data) {
	var _num = Math.round(parseFloat(data * 100));
	var result = _num.toString() + "%";
	return result;
}

/**
 * TODO:禁止滑动手势
 */
function forbidMove(flag) {
	if (flag) {
		$(document).on('touchmove', function(event) {
			event.preventDefault();
		});
	} else {
		$(document).off('touchmove');
	}
}

/**
 * TODO: 获取并设置当前时间方法
 */
function setThisTime(flag) {
	var newDate = new Date();
	var _year = newDate.getFullYear();
	var _month = newDate.getMonth() + 1;
	var _day = newDate.getDate();
	var today = '';
	today += _year + '-';
	if (_month >= 10) {
		today += _month;
	} else {
		today += "0" + _month;
	}
	if (flag != 'month') {
		today += '-'
		if (_day >= 10) {
			today += _day;
		} else {
			today += "0" + _day;
		}
	}
	// console.log(today);
	return today;
}

/**
 * TODO:时间格式转换
 */
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, // 小时
		"H+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds() // 毫秒
	};
	var week = {
		"0" : "\u65e5",
		"1" : "\u4e00",
		"2" : "\u4e8c",
		"3" : "\u4e09",
		"4" : "\u56db",
		"5" : "\u4e94",
		"6" : "\u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt
				.replace(
						RegExp.$1,
						((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f"
								: "\u5468")
								: "")
								+ week[this.getDay() + ""]);
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
}


/**
 * TODO:数字千分位格式化（列表页面用）
 */
Handlebars.registerHelper('formatMoney', function(val) {
	if (val) {
		return formatMoney(val);
	}
});

/**
 * TODO:数字千分位格式化
 */
function formatMoney(val) {
	return val.replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
}



/**
 * 自定义扩展
**/

/**
 * TODO:Handlebars比较方法
 */
Handlebars.registerHelper('compare', function(left, operator, right, options) {
	if (arguments.length < 3) {
		throw new Error('Handlerbars Helper "compare" needs 2 parameters');
	}
	var operators = {
		'==' : function(l, r) {
			return l == r;
		},
		'===' : function(l, r) {
			return l === r;
		},
		'!=' : function(l, r) {
			return l != r;
		},
		'!==' : function(l, r) {
			return l !== r;
		},
		'<' : function(l, r) {
			return l < r;
		},
		'>' : function(l, r) {
			return l > r;
		},
		'<=' : function(l, r) {
			return l <= r;
		},
		'>=' : function(l, r) {
			return l >= r;
		},
		'typeof' : function(l, r) {
			return typeof l == r;
		},
		'&&' : function(l, r) {
			return  l && r;
		},
		'||' : function(l, r) {
			return  l || r;
		}
	};
	if (!operators[operator]) {
		throw new Error(
				'Handlerbars Helper "compare" doesn\'t know the operator '
						+ operator);
	}
	var result = operators[operator](left, right);
	if (result) {
		return options.fn(this);
	} else {
		return options.inverse(this);
	}
});

/**
 * TODO:绑定Handlebars获取头像
 */
Handlebars.registerHelper('getFace', function(face, name) {
	if (face && face.indexOf('default-face-small.png') == -1) {
		return '<img class="face" src="' + face + '"/>';
	} else {
		return '<span class="face">' + (name ? name.substr(0, 1) : '') + '</span>';
	}
});