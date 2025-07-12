package ru.gb.jdk.one.online;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ru.gb.jdk.one.online.common.*;

/**
 * Главное окно приложения "Крестики-нолики".
 * <p>
 * Отображает игровое поле и панель с кнопками управления (новая игра, выход).
 * Позволяет открыть окно настроек и начать новую игру.
 * </p>
 *
 * @author Вы 🙂
 */
public class GameWindow extends JFrame {

    /**
     * Кнопка запуска новой игры.
     */
    private JButton btnStart;

    /**
     * Кнопка выхода из приложения.
     */
    private JButton btnExit;

    /**
     * Игровое поле, отображающее текущую игру.
     */
    private Map map;

    /**
     * Окно с настройками игры (выбор режима, размеров и т.д.).
     */
    private final SettingsWindow settingsWindow;

    /**
     * Конструктор главного окна.
     * <p>
     * Инициализирует все компоненты, слушатели и отображает окно.
     * </p>
     */
    public GameWindow() {
        initializeWindow();        // Настройка окна
        initializeComponents();    // Создание кнопок и карты
        setupListeners();          // Обработка событий
        layoutComponents();        // Расположение компонентов
        setVisible(true);          // Показ окна

        settingsWindow = new SettingsWindow(this); // Инициализация окна настроек
    }

    /**
     * Настраивает параметры основного окна: размер, заголовок, поведение при закрытии.
     */
    private void initializeWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setLocationRelativeTo(null); // Центрирует окно
        setTitle(Constants.WINDOW_SETTINGS_TITLE);
        setResizable(false);
    }

    /**
     * Инициализирует графические компоненты: кнопки и игровое поле.
     */
    private void initializeComponents() {
        btnStart = new JButton(Enums.ButtonText.NEW_GAME.getValue());
        btnExit = new JButton(Enums.ButtonText.EXIT.getValue());
        map = new Map();
    }

    /**
     * Устанавливает обработчики событий для кнопок.
     */
    private void setupListeners() {
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsWindow.setVisible(true); // Показывает окно настроек
            }
        });

        btnExit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Закрывает приложение
            }
        });
    }

    /**
     * Располагает компоненты в окне: кнопки снизу, игровое поле в центре.
     */
    private void layoutComponents() {
        JPanel panelBottom = new JPanel(new GridLayout(1, 2));
        panelBottom.add(btnStart);
        panelBottom.add(btnExit);
        add(panelBottom, BorderLayout.SOUTH);
        add(map);
    }

    /**
     * Запускает новую игру с заданными параметрами.
     *
     * @param mode   режим игры (человек против ИИ или человек против человека)
     * @param sizeX  ширина игрового поля
     * @param sizeY  высота игрового поля
     * @param winLen длина последовательности для победы
     */
    public void startNewGame(Enums.GameMode mode, int sizeX, int sizeY, int winLen) {
        map.startNewGame(mode, sizeX, sizeY, winLen);
    }
}
