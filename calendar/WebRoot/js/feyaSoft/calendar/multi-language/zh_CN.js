
Ext.ns("Ext.ux.calendar");

Ext.ux.calendar.Language = {
    'CalendarWin':{
        'title':'日程管理',
        'loadMask.msg':'欢迎使用日程管理，请稍候...'
    },

    'MainPanel':{
        'loadMask.msg':'数据加载中...'
    },

    'CalendarContainer':{
        'todayBtn.text':'今天',
        'dayBtn.text':'日模式',
        'weekBtn.text':'周模式',
        'monthBtn.text':'月模式',
        'weekMenu.showAll.text':'显示全部',
        'weekMenu.onlyWeek.text':'显示工作日',
        'monthMenu.showAll.text':'显示全部',
        'monthMenu.onlyWeek.text':'显示工作日',
        'moreMenu.setting.text':'设置',
        'moreMenu.about.text':'关于我的日历',
        'moreBtn.text':'其他',
        'searchCriteria.text':'搜索',
        'moreMenu.showAlert.text':'启动提醒功能',
        'moreMenu.language.text':'语言设置'
    },

    'WestPanel':{
        'myShowAllBtn.text':'显示全部',
        'myAddBtn.text':'新的日程分类',
        'myCalendarPanel.title':'我的日程分类'
    },

    'NorthPanel':{
        'myShowAllBtn.text':'显示全部',
        'myAddBtn.text':'新建分类',
        'myCalendarPanel.title':'我的日程分类'
    },
    
    'EventHandler':{
        'showOnlyItem.text':'仅显示此类日程',
        'viewItem.hide.text':'隐藏',
        'viewItem.show.text':'显示',
        'editItem.text':'编辑',
        'deleteItem.text':'删除',
        'hideItem.text':'不在日程栏中显示',
        'cancleGZ.text':'取消关注',
        'clearItem.text':'清空此类日程',
        'wholeDay':'全天',
        'untitled':'无主题',
        'unlockItem.text':'解锁',
        'lockItem.text':'锁定',
        'editEvent.title':'编辑事件',
        'deleteEvent.title':'删除事件',
        'more':'更多',
        'deleteRepeatPopup.title':'确认',
        'deleteRepeatPopup.msg':'点击"是"删除当前事件的所有重复事件, 点击"否"仅删除当前事件',
        //'deleteRepeatPopup.msg':'Click "Yes" to delete all this repeat events, or click "No" to only delete the current event?',
        'updateRepeatPopup.title':'确认',
        //'updateRepeatPopup.msg':'Click "Yes" to update for all this repeat events, or click "No" to only update for the current event?'
        'updateRepeatPopup.msg':'点击"是"更新当前事件的所有重复事件, 点击"否"仅更新当前事件'
    },
    
    'Editor':{
        'new.title':'新建事件',
        'edit.title':'编辑事件',
        'startDayField.label':'时间',
        'endDayField.label':'到',
        'startDayFieldN.label':'开始时间',
        'endDayFieldN.label':'结束时间',
        'wholeField.label':'全天',
        'subjectField.label':'主题',
        'contentField.label':'内容',
        'calendarField.label':'日程分类',
        'alertCB.label':'进行提醒',
        'alertCB.label1':'工作台提醒',
        'alertCB.label2':'邮件提醒',
        'lockCB.label':'锁定',
        'yaoqingCB.label':'邀请',
        'visibleScopeCheckbox.label':'可见范围',
        'personCB.label':'邀请人员',
        'adminCB.label':'邀请部室',
        'deleteBtn.text':'删除',
        'saveBtn.text':'保存',
        'cancelBtn.text':'取消',
        'new.title':'新建事件',
        'edit.title':'维护事件',
        'repeatTypeField.label':'重复类型',//（Repeat Type）
        'repeatIntervalField.label':'间隔',//(Recur every)
        'intervalUnitLabel.day.text':' 天 间隔 ',
        'intervalUnitLabel.week.text':' 周 间隔',
        'intervalUnitLabel.month.text':' 月 间隔',
        'intervalUnitLabel.year.text':' 年 间隔',
        'detailSetting':'修改详细信息...',
        'returnBtn.text':'返回',
        'startAndEnd':'Start and End',
        'repeatStartField.label':'起始日期',//(Start)
        'repeatNoEndRG.label':'无截止日期',//(No end date)
        'repeatEndTimeRG.label':'重复次数',//(End after)
        'repeatEndDateRG.label':'截止日期',//(End by)
        'repeatEndTimeUnit':'执行',//(occurrence(s))
        'weekCheckGroup.label':'提醒工作日',//(Repeat Day)
        'monthRadioGroup.label':'类型',//(Repeat By)
        'repeatByDate':'日',//Date
        'repeatByDay':'星期',//Day
        'alertLabel':'提醒事件设置',
        'alertEarly.label':'',
        'alertEarly.label2':'之前',
        'newAlertBtn.text':'New',
        'deleteAlertBtn.label':'清除',
//        'emailAlertEarlyInvalid':'The early time of Email Alert should be larger than 30 minutes.',
//        'popupAlertEarlyInvalid':'The early time of Popup Alert should be less than 24 hours.',
		'emailAlertEarlyInvalid':'邮件提醒事件的最小时间应该大于30分钟',
        'popupAlertEarlyInvalid':'弹出提醒事件的最小时间应该小于24小时',
        'repeatIntervalInvalid':'当前值必须为正整数!',
        'repeatBeginDayInvalid':'重复类型的开始时间不能晚于截止时间',
        'repeatEndDayInvalid':'重复类型的截止时间不能早于开始时间',
        'repeatTimeInvalid':'当前值必须为正整数!',
        'acceptBtn.text':'接受',
        'rejectBtn.text':'不接受',
        'repeatDayInfo' : '七天'
    },

    'CalendarEditor':{
        'new.title':'新建日程分类',
        'edit.title':'编辑日程分类',
        'nameField.label':'名称',
        'descriptionField.label':'描述',
        'clearBtn.text':'清除',
        'saveBtn.text':'保存',
        'cancelBtn.text':'取消'
    },

    'ExpirePopup':{
    	'From':'从',
    	'To':'到',
    	'editEvent':'点击查看',
        'title':'提醒事件',
        'hideCB.label':'不再显示',
        'tpl.calendar':'日程分类',
        'tpl.subject':'主题',
        'tpl.content':'内容',
        'tpl.leftTime':'剩余时间',
        'hour':'小时',
        'minute':'分钟',
        'untitled':'无主题',
        'noContent':'无内容'
    },

    'SettingPopup':{
        'title':'系统设置',
        'hourFormatField.label':'小时格式',
        'dayFormatField.label':'日期格式（日模式）',
        'weekFormatField.label':'日期格式（周模式）',
        'monthFormatField.label':'日期格式（月模式）',
        'applyBtn.text':'应用',
        'resetBtn.text':'重置',
        'closeBtn.text':'关闭',
        'fromtoFormatField.label':'日期格式（当前范围）',
        'scrollStartRowField.label':'开始时间（日周模式滚动条）',
        'languageField.label':'语言',
        'generalForm.title':'基本',
        'dwViewForm.title':'日模式|周模式',
        'monthViewForm.title':'月模式',
        'createByDblClickField.label':'双击新建事件',
        'singleDayField.label':'不允许跨天的活动',
        'weekStartDayField.label': '每周起始日期',
        'activeStartTimeField.label':'提醒起始时间',
        'activeEndTimeField.label':'提醒结束时间',
        'hideInactiveTimeField.label':'隐藏非工作时间',
        'readOnlyField.label':'是否只读',
        'intervalField.label':'计时器',
        'startEndInvalid':'工作开始时间必须早于工作下班时间!',
        'formatInvalid':'Example: 09:00',
        'initialViewField.label':'初始化模式'
    },

    'ResultView':{
        'cm.date':'日期',
        'cm.calendar':'日历',
        'cm.time':'时间',
        'cm.subject':'主题',
        'cm.content':'内容',
        'cm.expire':'剩余时间',
        'groupBtn.group.text':'分组',
        'groupBtn.unGroup.text':'取消分组',
        'returnBtn.text':'返回',
        'hour':'小时',
        'noSubject':'(无主题)',
        'noContent':'(无内容)',
        'loadMask.msg':'请等待...'
    },

    'DayView':{
        'loadMask.msg':'请等待...',
        'addItem.text':'新建事件',
        'events':'事件'
    },

    'MonthView':{
        'loadMask.msg':'请等待...',
        'overview':'情况',
        'showingEvents':'显示事件',
        'totalEvents':'全部事件',
//      'dayPre':'周',
        'dayPre':'',
        'addItem.text':'新建事件',
        'clearItem.text':'清空事件',
        'cutItem.text':'剪切',
        'copyItem.text':'拷贝',
        'pasteItem.text':'粘贴',
        'events':'事件'
    },

    'Mask':{
        '12Hours':'12 小时',
        '24Hours':'24 小时',
        'ar': '阿拉伯语',
        'de': '德语',
        'en':'American English',
        'es': '西班牙',
        'fr': 'Français',
        'it': 'Italiano',
        'ja': '日语',
        'lt': 'Lietuvių',
        'nl': 'Nederlandse',
        'pl': 'Polski',
        'pt': '葡萄牙语',
        'ru': '俄文',
        'zh_CN':'简体中文',
        'enable':'是',
        'disable':'否',
        'minute':'分钟',
        'monday':'星期一',
        'sunday':'星期天',
        'permitData':[
            [0, 'Read, Write and Share'],
            [1, 'Read and Write'],
            [2, 'Read only']
        ],
        'alertType':[
            ['popup', '工作台提醒'],
            ['email', '邮件提醒']
        ],
        'popupAlertUnit':[
            ['minute', '分钟'],
            ['hour', '小时']
        ],
        'alertUnit':[
            ['minute', 'Minute(s)'],
            ['hour', 'Hour(s)'],
            ['day', 'Day(s)'],
            ['wek', 'Week(s)']
        ],
        'initialViewInt':[
            [0, '日模式'],
            [1, '周模式'],
            [2, '月模式']
        ],
        'initialViewString':[
                          ['0', '日模式'],
                          ['1', '周模式'],
                          ['2', '月模式']
                      ]
    },

    repeatType:[
        ['no', '无'],
        ['day', '天'],
        ['week', '周'],
        ['month', '月'],
        ['year', '年']
    ],

    getWeekDayInMonth:function(date){
        var d =Ext.Date.format(date,'d');
        var w = Math.floor(d/7)+1;
        var wd = Ext.Date.format(date,'l');
        var str = '第 '+w;
        if(1 == w){
            str += '个';//st
        }else if(2 == w){
            str += '个';
        }else if(3 == w){
            str += '个';//rd
        }else{
            str += '个';//th
        }
        return str+' 星期'+wd;
    },

    getIntervalText:function(rtype, intervalSlot){
        var str = '';
        if('day' == rtype){
            if(1 == intervalSlot){
                str = '每天';
            }else{
                str = '每 '+intervalSlot+' 天';
            }
        }else if('week' == rtype){
            if(1 == intervalSlot){
                str = '每周';
            }else{
                str = '每 '+intervalSlot+' 个星期的 ';
            }
        }else if('month' == rtype){
            if(1 == intervalSlot){
                str = '每月 ';
            }else{
                str = '每 '+intervalSlot+' 个月的 ';
            }
        }else if('year' == rtype){
            if(1 == intervalSlot){
                str = '每年 ';
            }else{
                str = '每 '+intervalSlot+' 年的 ';
            }
        }
        return str;
    }
};

Ext.apply(Ext.ux.calendar.Mask, Ext.ux.calendar.Language);