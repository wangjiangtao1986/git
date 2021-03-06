package com.wangjt.calendar.mysql.dao.model;

public class CalendarEventReminder {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column calendar_event_reminder.ID
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column calendar_event_reminder.EVENT_ID
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    private String eventId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column calendar_event_reminder.TYPE
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column calendar_event_reminder.EARLY
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    private String early;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column calendar_event_reminder.UNIT
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    private String unit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column calendar_event_reminder.ALERTED
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    private String alerted;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column calendar_event_reminder.ID
     *
     * @return the value of calendar_event_reminder.ID
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column calendar_event_reminder.ID
     *
     * @param id the value for calendar_event_reminder.ID
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column calendar_event_reminder.EVENT_ID
     *
     * @return the value of calendar_event_reminder.EVENT_ID
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column calendar_event_reminder.EVENT_ID
     *
     * @param eventId the value for calendar_event_reminder.EVENT_ID
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    public void setEventId(String eventId) {
        this.eventId = eventId == null ? null : eventId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column calendar_event_reminder.TYPE
     *
     * @return the value of calendar_event_reminder.TYPE
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column calendar_event_reminder.TYPE
     *
     * @param type the value for calendar_event_reminder.TYPE
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column calendar_event_reminder.EARLY
     *
     * @return the value of calendar_event_reminder.EARLY
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    public String getEarly() {
        return early;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column calendar_event_reminder.EARLY
     *
     * @param early the value for calendar_event_reminder.EARLY
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    public void setEarly(String early) {
        this.early = early == null ? null : early.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column calendar_event_reminder.UNIT
     *
     * @return the value of calendar_event_reminder.UNIT
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column calendar_event_reminder.UNIT
     *
     * @param unit the value for calendar_event_reminder.UNIT
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column calendar_event_reminder.ALERTED
     *
     * @return the value of calendar_event_reminder.ALERTED
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    public String getAlerted() {
        return alerted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column calendar_event_reminder.ALERTED
     *
     * @param alerted the value for calendar_event_reminder.ALERTED
     *
     * @mbggenerated Mon Apr 09 10:08:15 CST 2018
     */
    public void setAlerted(String alerted) {
        this.alerted = alerted == null ? null : alerted.trim();
    }
}