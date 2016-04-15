var gmu = gmu || {
	version : '@version',
	$ : window.Zepto,
	staticCall : (function($) {
		var proto = $.fn, slice = [].slice, instance = $();

		instance.length = 1;

		return function(item, fn) {
			instance[0] = item;
			return proto[fn].apply(instance, slice.call(arguments, 2));
		};
	})(Zepto)
};