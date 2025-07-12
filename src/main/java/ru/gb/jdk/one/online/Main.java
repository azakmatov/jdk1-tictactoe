package ru.gb.jdk.one.online;

/**
 * Главный класс приложения "Крестики-нолики".
 * <p>
 * Запускает графическое окно {@link GameWindow}, с которого начинается игра.
 * </p>
 *
 * @author Вы 🙂
 */
public class Main {

    /**
     * Точка входа в приложение.
     * <p>
     * Создает новое игровое окно {@link GameWindow}.
     * </p>
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        new GameWindow(); // Запуск главного окна
    }
}

