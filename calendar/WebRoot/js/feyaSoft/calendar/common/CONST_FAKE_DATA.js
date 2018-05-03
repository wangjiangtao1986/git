

Ext.ns("Ext.ux.calendar");

Ext.ux.calendar.CONST = {
    /*
     * The version number of myCalendar
     */
    VERSION:'2.6.1',
    /*
     *true to show the language submenu in myCalendar, or not
     *
     */
    SHOW_LANGUAGE_MENU:true,
        
    /*
     *define the main path of myCalendar
     */
    MAIN_PATH:'js/feyaSoft/calendar/',
    
    BLANK_IMAGE_URL:'../../extjs/resources/images/default/s.gif',
    /*
     *define the multi-language path of myCalendar
     */
    CALENDAR_LANGUAGE_PATH:'js/feyaSoft/calendar/multi-language/',
    /*
     *define the multi-language path of EXT
     */
    EXT_LANGUAGE_PATH:'js/extjs/locale/',
    /*
     * define the some url here for datasource
     */
    searchURL:'listPage/search.html',

    showAllCalendarURL:'showAllCalendar.html',

    showOnlyCalendarURL:'showOnlyCalendar.html',

//保存日程分类
    createUpdateCalendarURL:'saveCalendarType.html',
    createUpdateCalendarURL2:'saveCalendarType2.html',

    deleteEventsByCalendarURL:'deleteEventsByCalendar.html',//清空事件（已测试）

    deleteCalendarURL:'deleteCalendar.html',  				//（已测试）

    loadCalendarURL:'loadCalendar.html',      				//(未测试)

    loadEventURL:'loadEvent.html',                       				//（已测试）

    loadRepeatEventURL:'loadRepeatEvent.html',				//使用日历内部刷新控件时被调用//(未测试)

    createEventURL:'createEditEvent.html',    				//工作台设置提醒逻辑待实现,较复杂（已测试）

    updateEventURL:'updateEvent.html',        				//工作台设置提醒逻辑待实现,较复杂（已测试）

    deleteEventURL:'deleteEvent.html',        				//（已测试）

    deleteRepeatEventURL:'deleteRepeatEvent.html',			//(未测试)

    changeDayURL:'changeDay.html',

    deleteDayURL:'deleteDay.html',

    loadSettingURL:'loadSetting.html',

    updateSettingURL:'updateSetting.html',


    createUpdateRepeatEventURL:'createUpdateRepeatEventURL.html',



    initialLoadURL:'initialLoad.html',

    listUserURL:'calendar/queryUser',
    updateLockStats:'updateLockStats.html'
    
    
    
};