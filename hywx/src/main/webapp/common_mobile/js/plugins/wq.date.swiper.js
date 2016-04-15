/**
 * @title 日历控件
 * @param 容器、最小滑动距离
 * @author gaojianjian
 * @date 2015-12-05
 */
var dateSwiper = function(container, range, callback){
	this.init(container,callback);
	this.box = document.getElementById(container);
	this.index = 1;
	this.moved = true;
	this.count = this.box.children.length;
	this.startX;
	this.endX;
	this.range = Number(range);
	this.bindTouchEvn();
	this.render(this.index);
}

dateSwiper.prototype.bindTouchEvn = function(){
	this.box.addEventListener('touchstart', this.touchstart.bind(this),false);
	this.box.addEventListener('touchmove', this.touchmove.bind(this),false);
	this.box.addEventListener('touchend', this.touchend.bind(this), false);
}

dateSwiper.prototype.touchstart = function(e){
	this.startX = 0;
	this.endX = 0;
	if (this.moved){
		var touch = e.touches[0];
	    this.startX = touch.pageX;
	    this.moved = false;
	}
}

dateSwiper.prototype.touchmove = function(e){
	e.preventDefault();
	var count = this.count;
	if (!this.moved){
		var touch = e.touches[0];
		this.endX = touch.pageX;
		if(this.startX - this.endX > 0){
			if (this.index > (count - 1)){
				this.index = 0;
			}
			this.index++;
			this.render(this.index);
		}else{
			if (this.index <= 1){
				this.index = count + 1
			}
			this.index--;
			this.render(this.index);
		}
		this.moved = true;
	}
}

dateSwiper.prototype.touchend = function(e){
	e.preventDefault();
	if (!this.moved){
		this.startX = 0;
		this.endX = 0;
		this.moved = true;
	}
}

dateSwiper.prototype.render = function(dir){
	var html = [], days = null, li = $('.calendar-body li');
	if (li.length > 0) {
		var date = $(li[li.length - 1]).data('date'), diff = dateSwiper.addDate(date, 2);
		if (dir == 0) {
			date = $(li[0]).data('date');
			diff = dateSwiper.addDate(date, -2);
		}
		days = dateSwiper.getMonToSun(diff);
	} else {
		days = dateSwiper.getMonToSun(new Date());
	}

	for (var i = 0; i < days.length; i++){
		html.push('<li class="ui-col" data-date="' + days[i].date + '">' + days[i].day + '</li>');
	}
	if ($('.calendar-body li').size() > 0 && $('.calendar-body li').size() < 14){
		if (dir == 0){
			$('.calendar-body').prepend(html.join('')).css({'margin-left': -$('body').width(), 'width': $('body').width() * 2}).animate({marginLeft: 0},500,'ease-in-out',function(){
				$('.calendar-body').css({width:'100%','margin-left':0}).html(html.join(''));
				$('.calendar-body li[data-date="' + dateSwiper.getCurrentDate() + '"]').addClass('ui-col-selected').append('<span class="split"></span>');
			});
		}else{
			$('.calendar-body').css('width', $('body').width() * 2).append(html.join('')).animate({marginLeft: -$('body').width()},500,'ease-in-out',function(){
				$('.calendar-body').css({width:'100%','margin-left':0}).html(html.join(''));
				$('.calendar-body li[data-date="' + dateSwiper.getCurrentDate() + '"]').addClass('ui-col-selected').append('<span class="split"></span>');
			});
		}
	} else{
		$('.calendar-body').html(html.join(''));
	}
	$('.calendar-body li[data-date="' + dateSwiper.getCurrentDate() + '"]').addClass('ui-col-selected').append('<span class="split"></span>');
}

dateSwiper.prototype.init = function(container, callback){
	var html = [], currentDate = dateSwiper.getCurrentDate(), d = currentDate.split('-');
	html.push('<div class="calendar-wrap ui-border-b">');
		html.push('<div class="calendar-nav">');
			html.push('<div class="calendar-nav-month">');
				html.push(dateSwiper.convertMonth(d[1]) + '月<span>' + d[0] + '年' + '</span>');
			html.push('</div>');
			html.push('<div class="calendar-nav-today">今</div>');
		html.push('</div>');
		html.push('<ul class="calendar-header ui-row-flex">');
			html.push('<li class="ui-col">周日</li>');
			html.push('<li class="ui-col">周一</li>');
			html.push('<li class="ui-col">周二</li>');
			html.push('<li class="ui-col">周三</li>');
			html.push('<li class="ui-col">周四</li>');
			html.push('<li class="ui-col">周五</li>');
			html.push('<li class="ui-col">周六</li>');
			html.push('</ul>');
		html.push('<ul id="' + container + '" class="calendar-body ui-row-flex"></ul>');
	html.push('</div>');
	$('.ui-container').prepend(html.join(''));
	
	if(callback){
		$(document).on('touchend','.calendar-body li',function(e){
			var date = $(this).data('date'), d = date.split('-');
			$('.calendar-body li span').remove();
			$('.calendar-body li').removeClass('ui-col-selected');
			$(this).addClass('ui-col-selected').append('<span class="split"></span>');
			$('.calendar-nav-month').html(dateSwiper.convertMonth(d[1]) + '月<span>' + d[0] + '年' + '</span>');
			callback(date);
			e.preventDefault();
		});
		
		$(document).on('touchend','.calendar-nav-today',function(){
			var html = [], days = dateSwiper.getMonToSun(new Date()), currentDate = dateSwiper.getCurrentDate(), d = currentDate.split('-');
			for (var i = 0; i < days.length; i++){
				html.push('<li class="ui-col" data-date="' + days[i].date + '">' + days[i].day + '</li>');
			}
			$('.calendar-body').html(html.join(''));
			$('.calendar-body li[data-date="' + dateSwiper.getCurrentDate() + '"]').addClass('ui-col-selected').append('<span class="split"></span>');
			$('.calendar-nav-month').html(dateSwiper.convertMonth(d[1]) + '月<span>' + d[0] + '年' + '</span>');
			callback(currentDate);
		});
	}
}

dateSwiper.getMonToSun = function(date) {
	var year = date.getFullYear();
	var month = date.getMonth();
	var dateOfWeek = date.getDay();
	var dateOfWeekInt = parseInt(dateOfWeek, 10);
	if (dateOfWeekInt == 0) {
		dateOfWeekInt = 6;
	}
	var aa = 6 - dateOfWeekInt;
	var tmp = parseInt(date.getDate(), 10);
	var sunDay = tmp + aa;
	var monDay = sunDay - 6
	var sun = tmp + aa - 6;
	var mon = tmp + aa - 5;
	var tue = tmp + aa - 4;
	var wed = tmp + aa - 3;
	var thu = tmp + aa - 2;
	var fri = tmp + aa - 1;
	var sat = tmp + aa;
	var sunDate = new Date(year, month, sun);
	var monDate = new Date(year, month, mon);
	var tueDate = new Date(year, month, tue);
	var wedDate = new Date(year, month, wed);
	var thuDate = new Date(year, month, thu);
	var friDate = new Date(year, month, fri);
	var satDate = new Date(year, month, sat);
	var result = new Array();
	result.push({day : sunDate.getDate(),date : sunDate.getFullYear() + '-' + (parseInt(sunDate.getMonth()) + 1) + '-' + sunDate.getDate()});
	result.push({day : monDate.getDate(),date : monDate.getFullYear() + '-' + (parseInt(monDate.getMonth()) + 1) + '-' + monDate.getDate()});
	result.push({day : tueDate.getDate(),date : tueDate.getFullYear() + '-' + (parseInt(tueDate.getMonth()) + 1) + '-' + tueDate.getDate()});
	result.push({day : wedDate.getDate(),date : wedDate.getFullYear() + '-' + (parseInt(wedDate.getMonth()) + 1) + '-' + wedDate.getDate()});
	result.push({day : thuDate.getDate(),date : thuDate.getFullYear() + '-' + (parseInt(thuDate.getMonth()) + 1) + '-' + thuDate.getDate()});
	result.push({day : friDate.getDate(),date : friDate.getFullYear() + '-' + (parseInt(friDate.getMonth()) + 1) + '-' + friDate.getDate()});
	result.push({day : satDate.getDate(),date : satDate.getFullYear() + '-' + (parseInt(satDate.getMonth()) + 1) + '-' + satDate.getDate()});
	return result;
}

dateSwiper.getCurrentDate = function() {
	var date = new Date();
	return date.getFullYear() + '-' + (parseInt(date.getMonth()) + 1) + '-' + date.getDate();
}

dateSwiper.convertMonth = function(month) {
	var mon = [ '一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二' ];
	if (month > 0) {
		return mon[month - 1];
	}
}

dateSwiper.addDate = function(date, day) {
	var d = new Date(date.replace(/-/g, '/'));
	d = d.valueOf();
	d = d + day * 24 * 60 * 60 * 1000;
	d = new Date(d);
	return d;
}