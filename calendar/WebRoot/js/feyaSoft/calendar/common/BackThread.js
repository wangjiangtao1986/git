

Ext.ns("Ext.ux.calendar");

Ext.ux.calendar.BackThread = function(config){
    Ext.apply(this, config);
    this.runner = new Ext.util.TaskRunner();
    this.timelineTask = {
        run:function(eh){
            var mp = eh.mainPanel;
            var cview = mp.calendarContainer.getLayout().activeItem;            
            if(cview instanceof Ext.ux.calendar.view.DayView){
                cview.setToday();
                cview.updateTimeline();
            }else if(cview instanceof Ext.ux.calendar.view.MonthView){
                cview.setToday();
            }
            eh.checkExpireEvents();
        },
        args:[this.ehandler],
        interval:60000
    };
    this.expireTask = {
        run:function(eh){
            var mp = eh.mainPanel;
            var cview = mp.calendarContainer.getLayout().activeItem;
            if(cview instanceof Ext.ux.calendar.view.ResultView){
                cview.list.getView().refresh();
            }
        },
        args:[this.ehandler],
        interval:1800000
    };
    Ext.ux.calendar.BackThread.superclass.constructor.call(this);
    this.runner.start(this.timelineTask);    
};

Ext.extend(Ext.ux.calendar.BackThread, Ext.util.Observable, {   
    destroy:function(){
        this.runner.stopAll();
    }
});