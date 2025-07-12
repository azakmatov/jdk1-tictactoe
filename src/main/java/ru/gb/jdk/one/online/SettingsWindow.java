package ru.gb.jdk.one.online;

import javax.swing.*;
import java.awt.*;

import ru.gb.jdk.one.online.common.*;

/**
 * Окно настроек игры, позволяющее пользователю выбрать режим игры,
 * размеры игрового поля и длину победной последовательности.
 * <p>
 * Предоставляет слайдеры и переключатели режимов, а также кнопку запуска новой игры.
 * Окно позиционируется по центру родительского окна {@link GameWindow}.
 */
public class SettingsWindow extends JFrame {

    /** Слайдер для выбора размера поля */
    private JSlider sizeFieldSlider;

    /** Слайдер для выбора длины победной последовательности */
    private JSlider sizeWinSlider;

    /** Метка, отображающая текущий размер поля */
    private JLabel sizeFieldLabel;

    /** Метка, отображающая текущую длину победной последовательности */
    private JLabel sizeWinLabel;

    /** Радиокнопка: режим человек против компьютера */
    private JRadioButton pvcRadioButton;

    /** Радиокнопка: режим человек против человека */
    private JRadioButton pvpRadioButton;

    /** Ссылка на главное игровое окно */
    private final GameWindow gameWindow;

    /**
     * Конструктор окна настроек.
     *
     * @param gameWindow главное игровое окно, относительно которого будет позиционироваться окно настроек
     */
    public SettingsWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        initializeWindow();
        layoutComponents();
    }

    /**
     * Инициализирует параметры окна: размеры, заголовок, позиция.
     */
    private void initializeWindow() {
        setSize(Constants.WINDOW_SETTINGS_WIDTH, Constants.WINDOW_SETTINGS_HEIGHT);
        setLayout(new BorderLayout());
        setTitle(Constants.WINDOW_SETTINGS_TITLE);
        setResizable(false);

        if (gameWindow != null && gameWindow.isShowing()) {
            Rectangle parentBounds = gameWindow.getBounds();
            int x = parentBounds.x + (parentBounds.width - getWidth()) / 2;
            int y = parentBounds.y + (parentBounds.height - getHeight()) / 2;
            setLocation(x, y);
        } else {
            setLocationRelativeTo(null);
        }
    }

    /**
     * Размещает на окне все панели и управляющие элементы.
     */
    private void layoutComponents() {
        JPanel settingsPanel = new JPanel(new GridLayout(3, 1));
        settingsPanel.add(createGameTypePanel());

        settingsPanel.add(createSliderPanel(
                Enums.Texts.FIELD_SIZE_LABEL.getValue(),
                Enums.Texts.SELECT_FIELD_SIZES.getValue(),
                Constants.FIELD_SIZE_MIN, Constants.FIELD_SIZE_MAX, Constants.FIELD_SIZE_MIN,
                (slider, label) -> {
                    int value = slider.getValue();
                    label.setText(Enums.Texts.SELECT_FIELD_SIZES.getValue() + value);
                    sizeWinSlider.setMaximum(value);
                },
                (slider, label) -> {
                    sizeFieldSlider = slider;
                    sizeFieldLabel = label;
                }
        ));

        settingsPanel.add(createSliderPanel(
                Enums.Texts.WIN_LENGTH_LABEL.getValue(),
                Enums.Texts.SELECT_FIELD_SIZES_FOR_WIN.getValue(),
                Constants.FIELD_SIZE_MIN, Constants.FIELD_SIZE_MAX, Constants.FIELD_SIZE_MIN,
                (slider, label) -> label.setText(Enums.Texts.SELECT_FIELD_SIZES_FOR_WIN.getValue() + slider.getValue()),
                (slider, label) -> {
                    sizeWinSlider = slider;
                    sizeWinLabel = label;
                }
        ));

        add(settingsPanel, BorderLayout.CENTER);
        add(createStartButton(), BorderLayout.SOUTH);
    }

    /**
     * Создает панель выбора режима игры (HVA или HVH).
     *
     * @return панель с радиокнопками режима
     */
    private JPanel createGameTypePanel() {
        pvcRadioButton = new JRadioButton(Enums.GameModeLabel.HVA.getValue(), true);
        pvpRadioButton = new JRadioButton(Enums.GameModeLabel.HVH.getValue());

        pvcRadioButton.setToolTipText(Constants.GAME_MODE_HVA_TOOLTIP);
        pvpRadioButton.setToolTipText(Constants.GAME_MODE_HVH_TOOLTIP);

        ButtonGroup group = new ButtonGroup();
        group.add(pvpRadioButton);
        group.add(pvcRadioButton);

        return createPanelWithComponents(
                Enums.Texts.GAME_MODE_LABEL.getValue(),
                pvcRadioButton,
                pvpRadioButton
        );
    }

    /**
     * Создает панель со слайдером и меткой.
     *
     * @param title         заголовок панели
     * @param labelPrefix   префикс метки
     * @param min           минимальное значение слайдера
     * @param max           максимальное значение слайдера
     * @param initial       начальное значение слайдера
     * @param listener      обработчик изменений слайдера
     * @param initListener  обработчик инициализации слайдера
     * @return панель с заголовком, меткой и слайдером
     */
    private JPanel createSliderPanel(String title, String labelPrefix, int min, int max, int initial,
                                     SliderChangeListener listener, SliderInitListener initListener) {
        JLabel label = new JLabel(labelPrefix + initial);
        JSlider slider = new JSlider(min, max, initial);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(e -> listener.stateChanged(slider, label));

        initListener.initialize(slider, label);

        return createPanelWithComponents(title, label, slider);
    }

    /**
     * Создает кнопку "Начать игру" с обработчиком событий.
     *
     * @return кнопка запуска игры
     */
    private JButton createStartButton() {
        JButton btnStart = new JButton(Enums.Texts.START_BUTTON.getValue());
        btnStart.addActionListener(e -> startNewGame());
        return btnStart;
    }

    /**
     * Создает панель с вертикальным расположением компонентов и заголовком.
     *
     * @param title      заголовок панели
     * @param components компоненты для добавления
     * @return панель с заголовком и компонентами
     */
    private JPanel createPanelWithComponents(String title, JComponent... components) {
        JPanel panel = new JPanel(new GridLayout(components.length + 1, 1));
        panel.add(new JLabel(title));
        for (JComponent component : components) {
            panel.add(component);
        }
        return panel;
    }

    /**
     * Запускает новую игру с выбранными настройками.
     * Выполняет проверку: длина победной последовательности не должна превышать размер поля.
     */
    private void startNewGame() {
        Enums.GameMode mode = pvcRadioButton.isSelected() ? Enums.GameMode.HVA : Enums.GameMode.HVH;
        int size = sizeFieldSlider.getValue();
        int winLength = sizeWinSlider.getValue();

        if (winLength > size) {
            JOptionPane.showMessageDialog(this,
                    Enums.Texts.ERROR_MESSAGE.getValue(),
                    Enums.Texts.ERROR_TITLE.getValue(),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        gameWindow.startNewGame(mode, size, size, winLength);
        setVisible(false);
    }

    /**
     * Функциональный интерфейс для обработки изменения значения слайдера.
     */
    @FunctionalInterface
    private interface SliderChangeListener {
        /**
         * Вызывается при изменении значения слайдера.
         *
         * @param slider слайдер
         * @param label  метка, отображающая значение
         */
        void stateChanged(JSlider slider, JLabel label);
    }

    /**
     * Функциональный интерфейс для инициализации слайдера и связанной с ним метки.
     */
    @FunctionalInterface
    private interface SliderInitListener {
        /**
         * Вызывается при создании слайдера, перед началом работы.
         *
         * @param slider слайдер
         * @param label  метка
         */
        void initialize(JSlider slider, JLabel label);
    }
}
