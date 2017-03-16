//jQuery(document).ready(
var timelineBar = timelineBar || {};
//timelineBar.imgPath = "";
timelineBar.status = "add";
timelineBar.eventsMinDistance = 60;
timelineBar.autoPlayPause = 1000;
timelineBar.AddRow = function ($, last) {
    var vframeList = $('#frame_list');
    var vNum = vframeList.children().length;//表格有多少个数据行
    var tmp = null;
    if(last && last == true) {
        tmp = "<li><a href='javascript:void(0);' class='addframe' data-date='03/03/2015' data-index=" + (vNum + 1) + ">" + (vNum + 1) + "</a></li>";
    } else {
        tmp = "<li><a href='javascript:void(0);' data-date='03/03/2015' data-index=" + (vNum + 1) + ">" + (vNum + 1) + "</a></li>";
    }
    vframeList.append(tmp);
    timelineBar.length = vNum + 1;
//    vframeList.children()[0].getElementsByTagName('a')[0].getAttribute("data-index")
//    vframeList.children()[0].getElementsByTagName('a')[0].childNodes[0].data = 4

}

timelineBar.DeleteRow = function ($) {
    var vframeList = $('#frame_list');
    var vNum = vframeList.children().length;//表格有多少个数据行
    if (vNum >= 2) {
        var obj = vframeList.children()[vNum - 1];
        obj.parentNode.removeChild(obj);
        vframeList.children()[vframeList.children().length - 1].getElementsByTagName('a')[0].setAttribute("class", "addframe");
    }
    timelineBar.length = vframeList.children().length;
}

timelineBar.DeleteAllRows = function ($) {
    var vframeList = $('#frame_list');
    var vNum = vframeList.children().length;//表格有多少个数据行
    while(vframeList.children().length > 0) {
        var obj = vframeList.children()[0];
        obj.parentNode.removeChild(obj);
    }
    timelineBar.length = vframeList.children().length;
}

timelineBar.triggle = function (element, method) {
    var func = element[method];
    return func();
}

timelineBar.initTimeline = function ($, timelines, addCallBack, loadCallBack, delCallBack) {
    timelines.each(function () {
        var timeline = $(this);
        timelineBar.timelineComponents = {};
        //cache timeline components
        timelineBar.timelineComponents['timelineWrapper'] = timeline.find('.events-box').find('.events-wrapper');
        timelineBar.timelineComponents['eventsWrapper'] = timelineBar.timelineComponents['timelineWrapper'].children('.events');
        timelineBar.timelineComponents['fillingLine'] = timelineBar.timelineComponents['eventsWrapper'].children('.filling-line');
        timelineBar.timelineComponents['timelineEvents'] = timelineBar.timelineComponents['eventsWrapper'].find('a');

//		timelineBar.timelineComponents['timelineDates'] = parseDate(timelineBar.timelineComponents['timelineEvents']);
//		timelineBar.timelineComponents['eventsMinLapse'] = minLapse(timelineComponents['timelineDates']);
        timelineBar.timelineComponents['timelineDates'] = timelineBar.parseFrameIdx(timelineBar.timelineComponents['timelineEvents']);
        timelineBar.timelineComponents['eventsMinLapse'] = timelineBar.minLapseFrameIdx(timelineBar.timelineComponents['timelineDates']);
        timelineBar.timelineComponents['timelineNavigation'] = timeline.find('.cd-timeline-navigation');
        timelineBar.timelineComponents['eventsContent'] = timeline.children('.events-content');

        //assign a left postion to the single events along the timeline
        timelineBar.setDatePosition(timelineBar.timelineComponents, timelineBar.eventsMinDistance);
        //assign a width to the timeline
        var timelineTotWidth = timelineBar.setTimelineWidth(timelineBar.timelineComponents, timelineBar.eventsMinDistance);
        //the timeline has been initialize - show it
        timeline.addClass('loaded');

        //detect click on the next arrow
        timelineBar.timelineComponents['timelineNavigation'].off('click', '.next');
        timelineBar.timelineComponents['timelineNavigation'].on('click', '.next', function (event) {
            event.preventDefault();
            timelineBar.updateSlide(timelineBar.timelineComponents, timelineTotWidth, 'next');
        });
        //detect click on the prev arrow
        timelineBar.timelineComponents['timelineNavigation'].off('click', '.prev');
        timelineBar.timelineComponents['timelineNavigation'].on('click', '.prev', function (event) {
            event.preventDefault();
            timelineBar.updateSlide(timelineBar.timelineComponents, timelineTotWidth, 'prev');
        });

        timelineBar.timelineComponents['eventsWrapper'].off('mouseover', 'a');
        //detect click on the a single event - show new event content
        timelineBar.timelineComponents['eventsWrapper'].on('mouseover', 'a', function (event, show_animation) {
            event.preventDefault();
            var delIndex = parseInt($(this).data('index'));
            if(timelineBar.status == "delete" && timelineBar.length != parseInt($(this).data('index'))) {
                timelineBar.timelineComponents['timelineEvents'].removeClass('selected');
                $(this).removeClass('older-event');
                $(this).addClass('todelete');
            } else {
                $(this).addClass('cushover');
            }
            //loadCallBack(delIndex, -1, true);
        });

        timelineBar.timelineComponents['eventsWrapper'].off('mouseout', 'a');
        //detect click on the a single event - show new event content
        timelineBar.timelineComponents['eventsWrapper'].on('mouseout', 'a', function (event, show_animation) {
            event.preventDefault();
            timelineBar.timelineComponents['timelineEvents'].removeClass('todelete');
            timelineBar.timelineComponents['timelineEvents'].removeClass('cushover');
        });

        timelineBar.timelineComponents['eventsWrapper'].off('click', 'a');
        //detect click on the a single event - show new event content
        timelineBar.timelineComponents['eventsWrapper'].on('click', 'a', function (event, show_animation) {
            event.preventDefault();
//			timelineBar.timer && clearTimeout(timelineBar.timer);
//			timelineBar.timer = setTimeout(function() {
//			
//			}, 300);
            if(com.previousSelectedObj != null){
            	com.previousSelectedObj.isSelected = false;
            	com.previousSelectedObj = null;
            	com.show();
            }
            
            var delIndex = parseInt($(this).data('index'));
            timelineBar.autoPlayStart = delIndex;
            if(timelineBar.Play == true && timelineBar.status == "delete") {
                timelineBar.Play = false;
                $('#auto_play').attr("src", timelineBar.imgPath+"/play.png");
                timelineBar.timer && clearInterval(timelineBar.timer);
                return;
            }
            if (event.originalEvent) {
            	timelineBar.todelete = $(this);
                timelineBar.Play = false;
                $('#auto_play').attr("src", timelineBar.imgPath+"/play.png");
                timelineBar.timer && clearInterval(timelineBar.timer);
            }
            
            if (timelineBar.length == parseInt($(this).data('index'))) {
            	timelineBar.todelete = null;
                if(timelineBar.status == "delete") {
                    //timelineBar.timelineComponents['timelineEvents'].removeClass('selected');
                    //$(this).addClass('selected');
                    return;
                }
                else {
                    timelineBar.timelineComponents['timelineEvents'].removeClass('addframe');
                    timelineBar.AddRow($, true);
                    timelineBar.show($, addCallBack, loadCallBack, delCallBack);
                    addCallBack(delIndex);
                    timelineBar.timelineComponents['timelineEvents'].removeClass('selected');
                    $(this).addClass('selected');
                }

            } else if (timelineBar.status == "delete") {
                timelineBar.DeleteRow($);
                timelineBar.show($, addCallBack, loadCallBack, delCallBack);
                delCallBack(delIndex);
                timelineBar.timelineComponents['timelineEvents'].removeClass('selected');
                if ($(this).attr("class").contains("addframe") == false) {
                    $(this).addClass('selected');
                    loadCallBack($(this).data('index'));
                } else {
                    var len = timelineBar.timelineComponents['timelineEvents'].length;
                    if (len > 1) {
                        var obj = $(timelineBar.timelineComponents['timelineEvents'][len - 2]);
                        //$(timelineBar.timelineComponents['timelineEvents'][len-2]).trigger('click');
                        obj.addClass('selected');
                        loadCallBack(obj.data('index'));
                    }
                }
            } else {
                //show_animation = true;
                if(show_animation && delIndex>1) {
                    loadCallBack(delIndex, delIndex-1);
                } else {
                    loadCallBack(delIndex);
                }
                timelineBar.timelineComponents['timelineEvents'].removeClass('selected');
                $(this).addClass('selected');
            }

            var translateValue = timelineBar.getTranslateValue(timelineBar.timelineComponents['eventsWrapper']),
            wrapperWidth = Number(timelineBar.timelineComponents['timelineWrapper'].css('width').replace('px', ''));
            if($(this).offset().left > ($('#events-wrapper').offset().left + ($('#events-wrapper').width()/2)) ) {
            	//$('.next').trigger('click', true);     
            	var tmd_dist = $(this).offset().left - ($('#events-wrapper').offset().left + ($('#events-wrapper').width()/2));
            	timelineBar.translateTimeline(timelineBar.timelineComponents, translateValue - tmd_dist + timelineBar.eventsMinDistance, tmd_dist - timelineTotWidth)
            } else {
            	//$('.prev').trigger('click', true);
            	var tmd_dist = ($('#events-wrapper').offset().left + ($('#events-wrapper').width()/2)) - $(this).offset().left;
            	timelineBar.translateTimeline(timelineBar.timelineComponents, translateValue + tmd_dist - timelineBar.eventsMinDistance);
            }
            
            timelineBar.updateOlderEvents($(this));
            timelineBar.updateFilling($(this), timelineBar.timelineComponents['fillingLine'], timelineTotWidth);
            timelineBar.updateVisibleContent($(this), timelineBar.timelineComponents['eventsContent']);

        });

//        timelineBar.timelineComponents['eventsWrapper'].off('dragstart', 'a');
//        //detect click on the a single event - show new event content
//        timelineBar.timelineComponents['eventsWrapper'].on('dragstart', 'a', function (event) {
//            var delIndex = parseInt($(this).data('index'));
//            event.originalEvent.dataTransfer.setData("Text", delIndex);
//            return;
//        });
//
//        $('#frame_delete').off('dragover');
//        $('#frame_delete').on('dragover', function (event) {
//            event.originalEvent.preventDefault();
//        });
//
//        $('#frame_delete').off('drop');
//        $('#frame_delete').on('drop', function (event) {
//            event.originalEvent.preventDefault();
//            var delIndex = event.originalEvent.dataTransfer.getData("Text");
//            if (timelineBar.length != delIndex) {
//                //不是最后一个就可以删除
//                timelineBar.DeleteRow($);
//                timelineBar.show($, addCallBack, loadCallBack, delCallBack);
//                delCallBack(delIndex);
//                timelineBar.timelineComponents['timelineEvents'].removeClass('selected');
//                var len = timelineBar.timelineComponents['timelineEvents'].length;
//                if (len > 1) {
//                    var obj = $(timelineBar.timelineComponents['timelineEvents'][len - 2]);
//                    //$(timelineBar.timelineComponents['timelineEvents'][len-2]).trigger('click');
//                    obj.addClass('selected');
//                    loadCallBack(obj.data('index'));
//                }
//            }
//            return;
//        });

        
        timelineBar.autoPlayStart = 1;
        timelineBar.Play = false;
        $('#auto_play').off('click');
        $('#auto_play').on('click', function (event) {
        	
        	timelineBar.autoPlayPause = $('#auto_play_interval').val()*1000;
        	
            if(com.previousSelectedObj != null){
            	com.previousSelectedObj.isSelected = false;
            	com.previousSelectedObj = null;
            	com.show();
            }
            if ($(this).attr("src") == timelineBar.imgPath+"/play.png") {
                $(this).attr("src", timelineBar.imgPath+"/stop.png");
                timelineBar.Play = true;
                timelineBar.timer = setInterval("timelineBar.autoPlay()", timelineBar.autoPlayPause);
            } else {
                $(this).attr("src", timelineBar.imgPath+"/play.png");
                timelineBar.Play = false;
                timelineBar.timer && clearInterval(timelineBar.timer);
            }
        });

        //on swipe, show next/prev event content
        timelineBar.timelineComponents['eventsContent'].off('swipeleft');
        timelineBar.timelineComponents['eventsContent'].on('swipeleft', function () {
            var mq = timelineBar.checkMQ();
            ( mq == 'mobile' ) && timelineBar.showNewContent(timelineBar.timelineComponents, timelineTotWidth, 'next');
        });
        timelineBar.timelineComponents['eventsContent'].off('swiperight');
        timelineBar.timelineComponents['eventsContent'].on('swiperight', function () {
            var mq = timelineBar.checkMQ();
            ( mq == 'mobile' ) && timelineBar.showNewContent(timelineBar.timelineComponents, timelineTotWidth, 'prev');
        });

        //keyboard navigation
        $(document).keyup(function (event) {
            if (event.which == '37' && elementInViewport(timeline.get(0))) {
                timelineBar.showNewContent(timelineBar.timelineComponents, timelineTotWidth, 'prev');
            } else if (event.which == '39' && elementInViewport(timeline.get(0))) {
                timelineBar.showNewContent(timelineBar.timelineComponents, timelineTotWidth, 'next');
            }
        });
    });
}

timelineBar.initClickData = function (frameIndex) {
    if(timelineBar.timelineComponents['timelineEvents'].length>1){
        var obj = $(timelineBar.timelineComponents['timelineEvents'][frameIndex-1]);
        obj.trigger('click');
    }
}

timelineBar.autoPlay = function () {
    if (timelineBar.Play == false) {
        timelineBar.timer && clearInterval(timelineBar.timer);
        return;
    }
    var len = timelineBar.timelineComponents['timelineEvents'].length;
    if (timelineBar.autoPlayStart < len) {
        var obj = $(timelineBar.timelineComponents['timelineEvents'][timelineBar.autoPlayStart - 1]);
        obj.trigger('click', true);
        timelineBar.autoPlayStart++;
    } else {
        timelineBar.autoPlayStart = 1;
    }
}

timelineBar.updateSlide = function (timelineComponents, timelineTotWidth, string) {
    //retrieve translateX value of timelineBar.timelineComponents['eventsWrapper']
    var translateValue = timelineBar.getTranslateValue(timelineBar.timelineComponents['eventsWrapper']),
        wrapperWidth = Number(timelineBar.timelineComponents['timelineWrapper'].css('width').replace('px', ''));
    //translate the timeline to the left('next')/right('prev')
    (string == 'next')
        ? timelineBar.translateTimeline(timelineBar.timelineComponents, translateValue - wrapperWidth + timelineBar.eventsMinDistance, wrapperWidth - timelineTotWidth)
        : timelineBar.translateTimeline(timelineBar.timelineComponents, translateValue + wrapperWidth - timelineBar.eventsMinDistance);
}

timelineBar.showNewContent = function (timelineComponents, timelineTotWidth, string) {
    //go from one event to the next/previous one
    var visibleContent = timelineBar.timelineComponents['eventsContent'].find('.selected'),
        newContent = ( string == 'next' ) ? visibleContent.next() : visibleContent.prev();

    if (newContent.length > 0) { //if there's a next/prev event - show it
        var selectedDate = timelineBar.timelineComponents['eventsWrapper'].find('.selected'),
            newEvent = ( string == 'next' ) ? selectedDate.parent('li').next('li').children('a') : selectedDate.parent('li').prev('li').children('a');

        timelineBar.updateFilling(newEvent, timelineBar.timelineComponents['fillingLine'], timelineTotWidth);
        timelineBar.updateVisibleContent(newEvent, timelineBar.timelineComponents['eventsContent']);
        newEvent.addClass('selected');
        selectedDate.removeClass('selected');
        timelineBar.updateOlderEvents(newEvent);
        timelineBar.updateTimelinePosition(string, newEvent, timelineBar.timelineComponents, timelineTotWidth);
    }
}

timelineBar.updateTimelinePosition = function (string, event, timelineComponents, timelineTotWidth) {
    //translate timeline to the left/right according to the position of the selected event
    var eventStyle = window.getComputedStyle(event.get(0), null),
        eventLeft = Number(eventStyle.getPropertyValue("left").replace('px', '')),
        timelineWidth = Number(timelineBar.timelineComponents['timelineWrapper'].css('width').replace('px', '')),
        timelineTotWidth = Number(timelineBar.timelineComponents['eventsWrapper'].css('width').replace('px', ''));
    var timelineTranslate = timelineBar.getTranslateValue(timelineBar.timelineComponents['eventsWrapper']);

    if ((string == 'next' && eventLeft > timelineWidth - timelineTranslate) || (string == 'prev' && eventLeft < -timelineTranslate)) {
        timelineBar.translateTimeline(timelineBar.timelineComponents, -eventLeft + timelineWidth / 2, timelineWidth - timelineTotWidth);
    }
}

timelineBar.translateTimeline = function (timelineComponents, value, totWidth) {
    var eventsWrapper = timelineBar.timelineComponents['eventsWrapper'].get(0);
    value = (value > 0) ? 0 : value; //only negative translate value
    value = ( !(typeof totWidth === 'undefined') && value < totWidth ) ? totWidth : value; //do not translate more than timeline width
    timelineBar.setTransformValue(eventsWrapper, 'translateX', value + 'px');
    //update navigation arrows visibility
    //(value == 0 ) ? timelineBar.timelineComponents['timelineNavigation'].find('.prev').addClass('inactive') : timelineBar.timelineComponents['timelineNavigation'].find('.prev').removeClass('inactive');
    //(value == totWidth ) ? timelineBar.timelineComponents['timelineNavigation'].find('.next').addClass('inactive') : timelineBar.timelineComponents['timelineNavigation'].find('.next').removeClass('inactive');
}

timelineBar.updateFilling = function (selectedEvent, filling, totWidth) {
    //change .filling-line length according to the selected event
    var eventStyle = window.getComputedStyle(selectedEvent.get(0), null),
        eventLeft = eventStyle.getPropertyValue("left"),
        eventWidth = eventStyle.getPropertyValue("width");
    eventLeft = Number(eventLeft.replace('px', '')) + Number(eventWidth.replace('px', '')) / 2;
    var scaleValue = eventLeft / totWidth;
    timelineBar.setTransformValue(filling.get(0), 'scaleX', scaleValue);
}

timelineBar.setDatePosition = function (timelineComponents, min) {
    for (i = 0; i < timelineBar.timelineComponents['timelineDates'].length; i++) {
        var distance = timelineBar.daydiff(timelineBar.timelineComponents['timelineDates'][0], timelineBar.timelineComponents['timelineDates'][i]);
        var distanceNorm = Math.round(distance / timelineBar.timelineComponents['eventsMinLapse']) + 1;
        timelineBar.timelineComponents['timelineEvents'].eq(i).css('left', distanceNorm * min + 'px');
    }
}

timelineBar.setTimelineWidth = function (timelineComponents, width) {
    var timeSpan = timelineBar.daydiff(timelineBar.timelineComponents['timelineDates'][0], timelineBar.timelineComponents['timelineDates'][timelineBar.timelineComponents['timelineDates'].length - 1]),
        timeSpanNorm = timeSpan / timelineBar.timelineComponents['eventsMinLapse'],
        timeSpanNorm = Math.round(timeSpanNorm) + 4;
    var totalWidth = Math.max($(timelineBar.timelineComponents['timelineWrapper']).width(), timeSpanNorm * width);
    timelineBar.timelineComponents['eventsWrapper'].css('width', totalWidth + 'px');
    timelineBar.updateFilling(timelineBar.timelineComponents['timelineEvents'].eq(0), timelineBar.timelineComponents['fillingLine'], totalWidth);

    return totalWidth;
}

timelineBar.updateVisibleContent = function (event, eventsContent) {
    var eventDate = event.data('date'),
        visibleContent = eventsContent.find('.selected'),
        selectedContent = eventsContent.find('[data-date="' + eventDate + '"]'),
        selectedContentHeight = selectedContent.height();

    if (selectedContent.index() > visibleContent.index()) {
        var classEnetering = 'selected enter-right',
            classLeaving = 'leave-left';
    } else {
        var classEnetering = 'selected enter-left',
            classLeaving = 'leave-right';
    }

    selectedContent.attr('class', classEnetering);
    visibleContent.attr('class', classLeaving).one('webkitAnimationEnd oanimationend msAnimationEnd animationend', function () {
        visibleContent.removeClass('leave-right leave-left');
        selectedContent.removeClass('enter-left enter-right');
    });
    eventsContent.css('height', selectedContentHeight + 'px');
}

timelineBar.updateOlderEvents = function (event) {
    event.parent('li').prevAll('li').children('a').addClass('older-event').end().end().nextAll('li').children('a').removeClass('older-event');
}

timelineBar.getTranslateValue = function (timeline) {
    var timelineStyle = window.getComputedStyle(timeline.get(0), null),
        timelineTranslate = timelineStyle.getPropertyValue("-webkit-transform") ||
            timelineStyle.getPropertyValue("-moz-transform") ||
            timelineStyle.getPropertyValue("-ms-transform") ||
            timelineStyle.getPropertyValue("-o-transform") ||
            timelineStyle.getPropertyValue("transform");

    if (timelineTranslate.indexOf('(') >= 0) {
        var timelineTranslate = timelineTranslate.split('(')[1];
        timelineTranslate = timelineTranslate.split(')')[0];
        timelineTranslate = timelineTranslate.split(',');
        var translateValue = timelineTranslate[4];
    } else {
        var translateValue = 0;
    }

    return Number(translateValue);
}

timelineBar.setTransformValue = function (element, property, value) {
    element.style["-webkit-transform"] = property + "(" + value + ")";
    element.style["-moz-transform"] = property + "(" + value + ")";
    element.style["-ms-transform"] = property + "(" + value + ")";
    element.style["-o-transform"] = property + "(" + value + ")";
    element.style["transform"] = property + "(" + value + ")";
}

//based on http://stackoverflow.com/questions/542938/how-do-i-get-the-number-of-days-between-two-dates-in-javascript
timelineBar.parseDate = function (events) {
    var dateArrays = [];
    events.each(function () {
        var dateComp = $(this).data('date').split('/'),
            newDate = new Date(dateComp[2], dateComp[1] - 1, dateComp[0]);
        dateArrays.push(newDate);
    });
    return dateArrays;
}

timelineBar.parseFrameIdx = function (events) {
    var dateArrays = [];
    events.each(function () {
        var dateComp = parseInt($(this).data('index'));
        dateArrays.push(dateComp * 30);
    });
    return dateArrays;
}

timelineBar.parseDate2 = function (events) {
    var dateArrays = [];
    events.each(function () {
        var singleDate = $(this),
            dateComp = singleDate.data('date').split('T');
        if (dateComp.length > 1) { //both DD/MM/YEAR and time are provided
            var dayComp = dateComp[0].split('/'),
                timeComp = dateComp[1].split(':');
        } else if (dateComp[0].indexOf(':') >= 0) { //only time is provide
            var dayComp = ["2000", "0", "0"],
                timeComp = dateComp[0].split(':');
        } else { //only DD/MM/YEAR
            var dayComp = dateComp[0].split('/'),
                timeComp = ["0", "0"];
        }
        var newDate = new Date(dayComp[2], dayComp[1] - 1, dayComp[0], timeComp[0], timeComp[1]);
        dateArrays.push(newDate);
    });
    return dateArrays;
}

timelineBar.daydiff = function (first, second) {
    return Math.round((second - first));
}

timelineBar.minLapse = function (dates) {
    //determine the minimum distance among events
    var dateDistances = [];
    for (i = 1; i < dates.length; i++) {
        var distance = daydiff(dates[i - 1], dates[i]);
        dateDistances.push(distance);
    }
    return Math.min.apply(null, dateDistances);
}

timelineBar.minLapseFrameIdx = function (dates) {
    //determine the minimum distance among events
    var dateDistances = [];
    for (i = 1; i < dates.length; i++) {
        var distance = timelineBar.daydiff(dates[i - 1], dates[i]);
        dateDistances.push(distance);
    }
    return Math.min.apply(null, dateDistances);
}
/*
 How to tell if a DOM element is visible in the current viewport?
 http://stackoverflow.com/questions/123999/how-to-tell-if-a-dom-element-is-visible-in-the-current-viewport
 */
timelineBar.elementInViewport = function (el) {
    var top = el.offsetTop;
    var left = el.offsetLeft;
    var width = el.offsetWidth;
    var height = el.offsetHeight;

    while (el.offsetParent) {
        el = el.offsetParent;
        top += el.offsetTop;
        left += el.offsetLeft;
    }

    return (
        top < (window.pageYOffset + window.innerHeight) &&
        left < (window.pageXOffset + window.innerWidth) &&
        (top + height) > window.pageYOffset &&
        (left + width) > window.pageXOffset
    );
}

timelineBar.checkMQ = function () {
    //check if mobile or desktop device
    return window.getComputedStyle(document.querySelector('.cd-horizontal-timeline'), '::before').getPropertyValue('content').replace(/'/g, "").replace(/"/g, "");
}

timelineBar.show = function ($, addCallBack, loadCallBack, delCallBack) {
    var timelines = $('.cd-horizontal-timeline');
    timelineBar.length = $('#frame_list').children().length;//表格有多少个数据行

    (timelines.length > 0) && timelineBar.initTimeline($, timelines, addCallBack, loadCallBack, delCallBack);
};
//);