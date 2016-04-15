(function() {
	var INTERVAL_MIN = 1000;
	var lastCalledTime = (new Date()).getTime();
	function throttle(handler) {
		return function() {
			var curTime = (new Date()).getTime();
			// 两次tap事件的间隔如果不大于1000
			if (curTime - lastCalledTime > INTERVAL_MIN) {
				lastCalledTime = curTime;
				handler.apply(this, arguments);
			}
		}
	}
	var oldOn = $.fn.on;
	$.fn.on = function(evt) {
		if (evt === 'tap') {
			var args = Array.prototype.slice.call(arguments);
			var handlerIndex;
			for ( var i = 0; i < args.length; i++) {
				if (typeof args[i] === 'function') {
					handlerIndex = i;
					break;
				}
			}
			args[handlerIndex] = throttle(args[handlerIndex]);
			this.on('click', function(e) {
				e.preventDefault();
			});
			return oldOn.apply(this, args);
		}
		return oldOn.apply(this, arguments);
	};
})(Zepto);