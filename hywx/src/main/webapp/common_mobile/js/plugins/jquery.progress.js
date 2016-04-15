;(function ($) {
    $.fn.loadingRing = function () {
        var defaultOpt = {
            trackColor: '#f0f0f0',
            progressColor: '#6ec84e',
            precent: 0,
            duration: 1500
        }; // 默认选项
        $(this).each(function () {
            var $target = $(this);
            var color = $target.data('color'); // 颜色
            var precent = parseInt($target.data('precent'), 10); // 百分比
            var duration = parseFloat($target.data('duration'), 10) * 1000; // 持续时间
            var trackColor, progressColor;
            if (color && color.split(',').length === 2) {
                var colorSet = color.split(',');
                trackColor = colorSet[0];
                progressColor = colorSet[1];
            } else {
                trackColor = defaultOpt.trackColor;
                progressColor = defaultOpt.progressColor;
            }
            if (!precent)
                precent = defaultOpt.precent;
            if (!duration)
                duration = defaultOpt.duration;

            $target.append('<div class="progress-track"></div><div class="progress-left"></div><div class="progress-right"></div><div class="progress-cover"></div><div class="progress-text"><span class="progress-num">' + precent +'</span><span class="progress-percent">%</span></div>');

            var x = $target.find('.progress-cover').height(); // 触发 Layout

            $target.find('.progress-track, .progress-cover').css('border-color', trackColor);
            $target.find('.progress-left, .progress-right').css('border-color', progressColor);

            $target.find('.progress-left').css({
                'transform': 'rotate(' + precent * 3.6 + 'deg)',
                '-o-transform': 'rotate(' + precent * 3.6 + 'deg)',
                '-ms-transform': 'rotate(' + precent * 3.6 + 'deg)',
                '-moz-transform': 'rotate(' + precent * 3.6 + 'deg)',
                '-webkit-transform': 'rotate(' + precent * 3.6 + 'deg)',
                'transition': 'transform ' + duration + 'ms linear',
                '-o-transition': '-o-transform ' + duration + 'ms linear',
                '-ms-transition': '-ms-transform ' + duration + 'ms linear',
                '-moz-transition': '-moz-transform ' + duration + 'ms linear',
                '-webkit-transition': '-webkit-transform ' + duration + 'ms linear'
            });

            if (precent > 50) {
                var animation = 'toggle ' + (duration * 50 / precent) + 'ms'
                $target.find('.progress-right').css({
                    'opacity': 1,
                    'animation': animation,
                    'animation-timing-function': 'step-end'
                });
                $target.find('.progress-cover').css({
                    'opacity': 0,
                    'animation': animation,
                    'animation-timing-function': 'step-start'
                });
            }
        });
    };
})(window.jQuery || window.Zepto || window.$);