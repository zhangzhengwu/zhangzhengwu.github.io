//Download by http://www.codefans.net
//每次只显示5个页码
(function($) {
    //设定页码方法，初始化
    $.fn.setPager = function(options) {
        var opts = $.extend({}, pagerDefaults, options);
        return this.each(function() {
            $(this).empty().append(setPagerHtml(parseInt(options.RecordCount), parseInt(options.PageIndex), options.buttonClick));

        });
    };
    //设定页数及html
    function setPagerHtml(RecordCount, PageIndex, pagerClick) {

        var $content = $("<ul class=\"pages\"></ul>");
        var startPageIndex = 1;
        //若页码超出
        if (RecordCount <= 0) RecordCount = pagerDefaults.PageSize;
        //末页
        var endPageIndex = parseInt(RecordCount % parseInt(pagerDefaults.PageSize)) > 0 ? parseInt(RecordCount / parseInt(pagerDefaults.PageSize)) + 1 : RecordCount / parseInt(pagerDefaults.PageSize)

        if (PageIndex > endPageIndex) PageIndex = endPageIndex;
        if (PageIndex <= 0) PageIndex = startPageIndex;
        var nextPageIndex = PageIndex + 1;
        var prevPageIndex = PageIndex - 1;
        if (PageIndex == startPageIndex) {
            $content.append($("<li><span class=\"beginEnd\">首页</span></li>"));
            $content.append($("<li><span class=\"beginEnd\">上一页</span></li>"));
        } else {

            $content.append(renderButton(RecordCount, 1, pagerClick, "首页"));
            $content.append(renderButton(RecordCount, prevPageIndex, pagerClick, "上一页"));
        }
        //这里判断是否显示页码
        if (pagerDefaults.ShowPageNumber) {
            // var html = "";
            //页码部分隐藏 只显示中间区域
            if (endPageIndex <= 5 && PageIndex <= 5) {
                for (var i = 1; i <= endPageIndex; i++) {
                    if (i == PageIndex) {
                        $content.append($("<li><span class=\"current\">" + i + "</span></li>"));
                    } else {
                        $content.append(renderButton(RecordCount, i, pagerClick, i));
                    }

                }

            } else if (endPageIndex > 5 && endPageIndex - PageIndex <= 2) {

                $content.append($("<li class=\"dotted\">...</li>"));
                for (var i = endPageIndex - 4; i <= endPageIndex; i++) {
                    if (i == PageIndex) {
                        $content.append($("<li><span class=\"current\">" + i + "</span></li>"));
                    } else {
                        $content.append(renderButton(RecordCount, i, pagerClick, i));
                    }

                }
            } else if (endPageIndex > 5 && PageIndex > 3) {

                $content.append($("<li class=\"dotted\">...</li>"));
                for (var i = PageIndex - 2; i <= PageIndex + 2; i++) {
                    if (i == PageIndex) {
                        $content.append($("<li><span class=\"current\">" + i + "</span></li>"));
                    } else {
                        $content.append(renderButton(RecordCount, i, pagerClick, i));
                    }

                }
                $content.append($("<li class=\"dotted\">...</li>"));

            } else if (endPageIndex > 5 && PageIndex <= 3) {

                for (var i = 1; i <= 5; i++) {
                    if (i == PageIndex) {
                        $content.append($("<li><span class=\"current\">" + i + "</span></li>"));
                    } else {
                        $content.append(renderButton(RecordCount, i, pagerClick, i));
                    }

                }
                $content.append($("<li class=\"dotted\">...</li>"));
            }
        }
        if (PageIndex == endPageIndex) {
            $content.append($("<li><span class=\"beginEnd\">下一页</span></li>"));
            $content.append($("<li><span class=\"beginEnd\">末页</span></li>"));
        } else {
            $content.append(renderButton(RecordCount, nextPageIndex, pagerClick, "下一页"));
            $content.append(renderButton(RecordCount, endPageIndex, pagerClick, "末页"));
        }


        return $content;
    }
    function renderButton(recordCount, goPageIndex, EventHander, text) {
        var $goto = $("<li><a title=\"第" + goPageIndex + "页\">" + text + "</a></li>\"");
        $goto.click(function() {

            EventHander(recordCount, goPageIndex);
        });
        return $goto;
    }
    var pagerDefaults = {
        DefaultPageCount: 1,
        DefaultPageIndex: 1,
        PageSize: 15,
        ShowPageNumber: true //是否显示页码
    };
})(jQuery);