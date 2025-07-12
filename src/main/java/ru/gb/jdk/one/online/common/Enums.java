package ru.gb.jdk.one.online.common;

/**
 * Класс содержит вложенные перечисления, используемые в различных частях игры —
 * от интерфейса до логики.
 * <p>
 * Все перечисления сгруппированы в одном классе Enums для удобства доступа и навигации.
 * </p>
 */
public class Enums {

    /**
     * Перечисление для текстов кнопок в главном окне.
     */
    public enum ButtonText {
        /** Текст кнопки "Новая игра" */
        NEW_GAME("New Game"),
        /** Текст кнопки "Выход" */
        EXIT("Exit");

        private final String value;

        ButtonText(String value) {
            this.value = value;
        }

        /**
         * Возвращает строковое представление текста кнопки.
         * @return текст кнопки
         */
        public String getValue() {
            return value;
        }
    }

    /**
     * Перечисление для различных меток и сообщений интерфейса.
     */
    public enum Texts {
        SELECT_FIELD_SIZES("Заданный размер поля: "),
        SELECT_FIELD_SIZES_FOR_WIN("Заданная длина для победы: "),
        GAME_MODE_LABEL("Выберите режим игры"),
        FIELD_SIZE_LABEL("Выберите размеры поля"),
        WIN_LENGTH_LABEL("Выберите длину для победы"),
        START_BUTTON("Начать новую игру"),
        ERROR_TITLE("Ошибка настроек"),
        ERROR_MESSAGE("Длина для победы не может быть больше размера поля");

        private final String value;

        Texts(String value) {
            this.value = value;
        }

        /**
         * Возвращает текст метки или сообщения.
         * @return строковое значение текста
         */
        public String getValue() {
            return value;
        }
    }

    /**
     * Перечисление для отображаемых названий режимов игры.
     */
    public enum GameModeLabel {
        /** Человек против компьютера */
        HVA("Человек против компьютера"),
        /** Человек против человека */
        HVH("Человек против человека");

        private final String value;

        GameModeLabel(String value) {
            this.value = value;
        }

        /**
         * Возвращает строковое описание режима игры.
         * @return название режима
         */
        public String getValue() {
            return value;
        }
    }

    /**
     * Перечисление возможных состояний игры.
     */
    public enum GameState {
        /** Игра продолжается */
        GAME(0),
        /** Победа игрока 1 */
        WIN_HUMAN(1),
        /** Победа игрока 2 */
        WIN_AI(2),
        /** Ничья */
        DRAW(3);

        private final int value;

        GameState(int value) {
            this.value = value;
        }

        /**
         * Возвращает числовое значение состояния игры.
         * @return целочисленное представление
         */
        public int getValue() {
            return value;
        }
    }

    /**
     * Перечисление сообщений о завершении игры.
     */
    public enum Message {
        /** Победа человека */
        WIN_HUMAN("Победил Игрок 1!"),
        /** Победа компьютера */
        WIN_AI("Победил Игрок 2!"),
        /** Игра завершилась ничьей */
        DRAW("Ничья!");

        private final String value;

        Message(String value) {
            this.value = value;
        }

        /**
         * Возвращает текст сообщения.
         * @return строка сообщения
         */
        public String getValue() {
            return value;
        }
    }

    /**
     * Перечисление, определяющее содержимое ячеек игрового поля.
     */
    public enum Dot {
        /** Ячейка занята игроком */
        HUMAN(1),
        /** Ячейка занята компьютером */
        AI(2),
        /** Ячейка пуста */
        EMPTY(0);

        private final int value;

        Dot(int value) {
            this.value = value;
        }

        /**
         * Возвращает числовое представление точки.
         * @return значение точки
         */
        public int getValue() {
            return value;
        }
    }

    /**
     * Перечисление доступных режимов игры.
     */
    public enum GameMode {
        /** Человек против компьютера */
        HVA(0),
        /** Человек против человека */
        HVH(1);

        private final int value;

        GameMode(int value) {
            this.value = value;
        }

        /**
         * Возвращает числовой код режима игры.
         * @return целочисленное значение
         */
        public int getValue() {
            return value;
        }
    }
}
