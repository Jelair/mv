
$(function () {
    var imgs = ($(".lazy-img"));
    var screenHeight = $(window).height();
    var onScroll = function () {
        var scrollTop = $(window).scrollTop();

        if (imgs.length > 0){
            var loadedIndex = [];
            $.each(imgs, function (i, val) {
                var elOffsetTop = $(val).offset().top;
                if (elOffsetTop - scrollTop < screenHeight){
                    $(val).attr("src", $(val).attr("lazyload"));
                    $(val).removeAttr("lazyload");
                    loadedIndex.unshift(i);
                    //console.log("picture show " + i);
                }
            });
            $.each(loadedIndex, function (index) {
                imgs.splice(index, 1);
            });
        }

    };
    $(window).scroll(onScroll);
    onScroll();
});