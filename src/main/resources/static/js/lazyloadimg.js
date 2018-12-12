
$(function () {
    var imgs = ($(".lazy-img"));
    var screenHeight = $(window).height();
    var loadedIndex = new Set();
    var onScroll = function () {
        var scrollTop = $(window).scrollTop();
        if (imgs.length > loadedIndex.size){
            $.each(imgs, function (i, val) {
                if ($(val).attr("lazyload")!=null){
                    var elOffsetTop = $(val).offset().top;
                    if (elOffsetTop - scrollTop < screenHeight){
                        $(val).attr("src", $(val).attr("lazyload"));
                        $(val).removeAttr("lazyload");
                        loadedIndex.add(i);
                    }
                }
            });

        }

    };
    $(window).scroll(onScroll);
    onScroll();
});