package ru.gb.jdk.one.online.common;

/**
 * Класс содержит набор неизменяемых констант, используемых в интерфейсе и логике игры.
 * <p>
 * Здесь собраны значения, связанные с размерами окна, параметрами поля, шрифтами и текстами сообщений.
 * </p>
 * <p>
 * Использование констант позволяет избежать "магических чисел" и строк в коде.
 * </p>
 *
 * @author Вы :)
 */
public class Constants {

    /** Отступы в пикселях */
    public static final int PADDING = 10;

    /** Минимальный размер игрового поля */
    public static final int FIELD_SIZE_MIN = 3;

    /** Максимальный размер игрового поля */
    public static final int FIELD_SIZE_MAX = 10;

    /** Название шрифта для сообщений */
    public static final String FONT_OF_MESSAGES = "Times New Roman";

    /** Размер шрифта сообщений */
    public static final int SIZE_OF_MESSAGES = 32;

    /** Ширина основного окна */
    public static final int WINDOW_WIDTH = 507;

    /** Высота основного окна */
    public static final int WINDOW_HEIGHT = 555;

    /** Ширина окна настроек */
    public static final int WINDOW_SETTINGS_WIDTH = 230;

    /** Высота окна настроек */
    public static final int WINDOW_SETTINGS_HEIGHT = 350;

    /** Заголовок окна настроек */
    public static final String WINDOW_SETTINGS_TITLE = "Настройки игры";

    /** Сообщение об ошибке при некорректном режиме игры */
    public static final String GAME_MODE_ERROR_LABEL = "Непредусмотренное состояние игры: ";

    /** Сообщение об ошибке при недопустимом значении в ячейке */
    public static final String ERROR_VALUE_IN_CELL_LABEL = "Недопустимое значение в ячейке";

    /** Подсказка для режима "Человек против компьютера" */
    public static final String GAME_MODE_HVA_TOOLTIP = "Вы играете против компьютера";

    /** Подсказка для режима "Человек против человека" */
    public static final String GAME_MODE_HVH_TOOLTIP = "Два игрока играют на одном устройстве";
}
