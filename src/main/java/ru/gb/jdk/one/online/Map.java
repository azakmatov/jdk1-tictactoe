package ru.gb.jdk.one.online;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import ru.gb.jdk.one.online.common.*;

/**
 * Класс {@code Map} представляет собой панель, на которой отображается игровое поле
 * и реализуется логика игры "Крестики-нолики" для различных режимов.
 * <p>
 * Поддерживаются режимы:
 * <ul>
 *     <li>Человек против компьютера (HVA)</li>
 *     <li>Человек против человека (HVH)</li>
 * </ul>
 *
 * @author —
 */
public class Map extends JPanel {

    /** Генератор случайных чисел для хода компьютера */
    private static final Random RANDOM = new Random();

    /** Размеры ячеек поля */
    private static int cellWidth, cellHeight;

    /** Текущий режим игры */
    private static Enums.GameMode mode;

    /** Размеры игрового поля */
    private static int fieldSizeX, fieldSizeY;

    /** Длина последовательности, необходимая для победы */
    private static int winLen;

    /** Игровое поле (0 - пусто, 1 - человек, 2 - компьютер) */
    private static int[][] field;

    /** Флаг, указывающий, идёт ли игра */
    private static boolean gameWork;

    /** Состояние игры */
    private static Enums.GameState gameState;

    /** Игрок, делающий текущий ход */
    private static Enums.Dot currentPlayer;

    /**
     * Конструктор. Устанавливает фоновый цвет и слушатель мыши для обработки ходов игроков.
     */
    public Map() {
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (gameWork && (mode == Enums.GameMode.HVA || mode == Enums.GameMode.HVH)) {
                    handlePlayerTurn(e);
                }
            }
        });
    }

    /**
     * Обрабатывает ход игрока при клике мыши.
     *
     * @param mouseEvent событие клика
     */
    private void handlePlayerTurn(MouseEvent mouseEvent) {
        int x = mouseEvent.getX() / cellWidth;
        int y = mouseEvent.getY() / cellHeight;
        if (!isValidCell(x, y) || !isEmptyCell(x, y)) return;

        if (mode == Enums.GameMode.HVH) {
            field[y][x] = currentPlayer.getValue();
            if (checkEndGame(currentPlayer.getValue(), currentPlayer == Enums.Dot.HUMAN ? Enums.GameState.WIN_HUMAN : Enums.GameState.WIN_AI)) return;
            currentPlayer = (currentPlayer == Enums.Dot.HUMAN) ? Enums.Dot.AI : Enums.Dot.HUMAN;
        } else if (mode == Enums.GameMode.HVA) {
            field[y][x] = Enums.Dot.HUMAN.getValue();
            if (checkEndGame(Enums.Dot.HUMAN.getValue(), Enums.GameState.WIN_HUMAN)) return;
            aiTurn();
            checkEndGame(Enums.Dot.AI.getValue(), Enums.GameState.WIN_AI);
        }

        repaint();
    }

    /**
     * Инициализирует игровое поле.
     */
    private void initMap() {
        field = new int[fieldSizeY][fieldSizeX];
    }

    /**
     * Проверяет, находится ли ячейка в пределах поля.
     *
     * @param x координата X
     * @param y координата Y
     * @return {@code true} если ячейка допустима
     */
    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Проверяет, пуста ли ячейка.
     *
     * @param x координата X
     * @param y координата Y
     * @return {@code true} если ячейка пуста
     */
    private boolean isEmptyCell(int x, int y) {
        return field[y][x] == Enums.Dot.EMPTY.getValue();
    }

    /**
     * Проверяет окончание игры (победа или ничья).
     *
     * @param dot       значение игрока (1 или 2)
     * @param gameState состояние победителя
     * @return {@code true} если игра окончена
     */
    private boolean checkEndGame(int dot, Enums.GameState gameState) {
        if (checkWin(dot)) {
            Map.gameState = gameState;
            repaint();
            gameWork = false;
            return true;
        }
        if (isMapFull()) {
            Map.gameState = Enums.GameState.DRAW;
            repaint();
            gameWork = false;
            return true;
        }
        return false;
    }

    /**
     * Проверяет, выиграл ли игрок.
     *
     * @param dot значение игрока
     * @return {@code true} если игрок выиграл
     */
    private boolean checkWin(int dot) {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (checkLine(x, y, 1, 0, winLen, dot) ||
                        checkLine(x, y, 0, 1, winLen, dot) ||
                        checkLine(x, y, 1, 1, winLen, dot) ||
                        checkLine(x, y, 1, -1, winLen, dot)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Проверяет прямую линию ячеек на наличие победной последовательности.
     *
     * @param x   начальная координата X
     * @param y   начальная координата Y
     * @param vx  направление по X
     * @param vy  направление по Y
     * @param len длина проверяемой последовательности
     * @param dot значение игрока
     * @return {@code true} если линия выигрышная
     */
    private boolean checkLine(int x, int y, int vx, int vy, int len, int dot) {
        int endX = x + (len - 1) * vx;
        int endY = y + (len - 1) * vy;
        if (!isValidCell(endX, endY)) return false;

        for (int i = 0; i < len; i++) {
            if (field[y + i * vy][x + i * vx] != dot) return false;
        }
        return true;
    }

    /**
     * Запускает новую игру с заданными параметрами.
     *
     * @param mode   режим игры
     * @param sizeX  ширина поля
     * @param sizeY  высота поля
     * @param winLen длина для победы
     */
    public void startNewGame(Enums.GameMode mode, int sizeX, int sizeY, int winLen) {
        Map.mode = mode;
        Map.fieldSizeX = sizeX;
        Map.fieldSizeY = sizeY;
        Map.winLen = winLen;
        initMap();
        gameWork = true;
        gameState = Enums.GameState.GAME;
        currentPlayer = Enums.Dot.HUMAN;
        repaint();
    }

    /**
     * Выполняет ход компьютера.
     */
    private void aiTurn() {
        if (tryWinMove(Enums.Dot.AI.getValue(), true)) return;
        if (tryWinMove(Enums.Dot.HUMAN.getValue(), false)) return;

        int x, y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isEmptyCell(x, y));

        field[y][x] = Enums.Dot.AI.getValue();
    }

    /**
     * Ищет выигрышный ход или блокирующий ход противника.
     *
     * @param dot   значение игрока
     * @param apply применять ли ход
     * @return {@code true} если найден выигрышный или блокирующий ход
     */
    private boolean tryWinMove(int dot, boolean apply) {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isEmptyCell(x, y)) {
                    field[y][x] = dot;
                    if (checkWin(dot)) {
                        if (!apply) {
                            field[y][x] = Enums.Dot.AI.getValue();
                        }
                        return true;
                    }
                    field[y][x] = Enums.Dot.EMPTY.getValue();
                }
            }
        }
        return false;
    }

    /**
     * Проверяет, заполнено ли поле.
     *
     * @return {@code true} если нет пустых ячеек
     */
    private boolean isMapFull() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == Enums.Dot.EMPTY.getValue()) return false;
            }
        }
        return true;
    }

    /**
     * Метод отрисовки компонента.
     *
     * @param g графический контекст
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (field == null) return;
        render(g);
    }

    /**
     * Отрисовывает сетку, крестики/нолики и сообщение о завершении игры.
     *
     * @param g графический контекст
     */
    private void render(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        cellWidth = width / fieldSizeX;
        cellHeight = height / fieldSizeY;

        drawGrid(g);
        drawCells(g);

        if (gameState != Enums.GameState.GAME) {
            showMessage(g);
        }
    }

    /**
     * Рисует сетку игрового поля.
     *
     * @param g графический контекст
     */
    private void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i <= fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, getWidth(), y);
        }
        for (int i = 0; i <= fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, getHeight());
        }
    }

    /**
     * Отрисовывает символы игроков на поле.
     *
     * @param g графический контекст
     */
    private void drawCells(Graphics g) {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == Enums.Dot.EMPTY.getValue()) continue;

                if (field[y][x] == Enums.Dot.HUMAN.getValue()) {
                    drawCross(g, x, y);
                } else if (field[y][x] == Enums.Dot.AI.getValue()) {
                    drawCircle(g, x, y);
                } else {
                    throw new RuntimeException(Constants.ERROR_VALUE_IN_CELL_LABEL + " (" + x + ", " + y + "): " + field[y][x]);
                }
            }
        }
    }

    /**
     * Отрисовывает крестик (ход человека).
     *
     * @param g графический контекст
     * @param x координата X
     * @param y координата Y
     */
    private void drawCross(Graphics g, int x, int y) {
        g.drawLine(x * cellWidth + Constants.PADDING, y * cellHeight + Constants.PADDING,
                (x + 1) * cellWidth - Constants.PADDING, (y + 1) * cellHeight - Constants.PADDING);
        g.drawLine(x * cellWidth + Constants.PADDING, (y + 1) * cellHeight - Constants.PADDING,
                (x + 1) * cellWidth - Constants.PADDING, y * cellHeight + Constants.PADDING);
    }

    /**
     * Отрисовывает кружок (ход компьютера).
     *
     * @param g графический контекст
     * @param x координата X
     * @param y координата Y
     */
    private void drawCircle(Graphics g, int x, int y) {
        g.drawOval(x * cellWidth + Constants.PADDING, y * cellHeight + Constants.PADDING,
                cellWidth - Constants.PADDING * 2, cellHeight - Constants.PADDING * 2);
    }

    /**
     * Отображает сообщение о результате игры.
     *
     * @param g графический контекст
     */
    private void showMessage(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, getHeight() / 2, getWidth(), 70);
        g.setColor(Color.YELLOW);
        g.setFont(new Font(Constants.FONT_OF_MESSAGES, Font.BOLD, Constants.SIZE_OF_MESSAGES));

        String msg;
        int msgX;
        switch (gameState) {
            case DRAW -> {
                msg = Enums.Message.DRAW.getValue();
                msgX = 180;
            }
            case WIN_HUMAN -> {
                msg = Enums.Message.WIN_HUMAN.getValue();
                msgX = 20;
            }
            case WIN_AI -> {
                msg = Enums.Message.WIN_AI.getValue();
                msgX = 70;
            }
            default -> throw new RuntimeException(Constants.GAME_MODE_ERROR_LABEL + gameState);
        }
        g.drawString(msg, msgX, getHeight() / 2 + 60);
    }
}
