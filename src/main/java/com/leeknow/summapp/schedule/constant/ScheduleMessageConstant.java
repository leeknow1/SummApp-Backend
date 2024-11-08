package com.leeknow.summapp.schedule.constant;

public class ScheduleMessageConstant {

    /**
     * Перезапуск всех задач в системе завершен., Жүйедегі барлық тапсырмаларды қайта іске қосу аяқталды.
     */
    public static final int RESTART_ALL_TASK_SUCCESSFULLY = 7;

    /**
     * Успешно остановлены все задачи в системе., Жүйедегі барлық тапсырмалар сәтті тоқтатылды.
     */
    public static final int STOPPED_ALL_TASKS_SUCCESSFULLY = 8;

    /**
     * Задача успешна запущена!, Тапсырма сәтті іске қосылды!
     */
    public static final int TASK_SUCCESSFULLY_STARTED = 9;

    /**
     * Не удалось найти задачу в базе данных!, Тапсырма дерекқордан табылмады!
     */
    public static final int TASK_NOT_FOUND_IN_DATABASE = 10;

    /**
     * Задача успешна остановлена!, Тапсырма сәтті тоқтатылды!
     */
    public static final int TASK_SUCCESSFULLY_STOPPED = 11;
}
