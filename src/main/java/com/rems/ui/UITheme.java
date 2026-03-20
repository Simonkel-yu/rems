package com.rems.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class UITheme {

    // Colors
    public static final Color BG_DARK       = new Color(18, 18, 18);
    public static final Color BG_PANEL      = new Color(28, 28, 28);
    public static final Color BG_CARD       = new Color(38, 38, 38);
    public static final Color ACCENT        = new Color(99, 179, 237);   // blue
    public static final Color ACCENT_HOVER  = new Color(66, 153, 225);
    public static final Color SUCCESS       = new Color(72, 187, 120);   // green
    public static final Color DANGER        = new Color(245, 101, 101);  // red
    public static final Color TEXT_PRIMARY  = new Color(237, 237, 237);
    public static final Color TEXT_MUTED    = new Color(160, 160, 160);
    public static final Color BORDER_COLOR  = new Color(55, 55, 55);

    // Fonts
    public static final Font FONT_TITLE     = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE  = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FONT_BODY      = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL     = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_BUTTON    = new Font("Segoe UI", Font.BOLD, 13);

    // Borders
    public static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        );
    }

    public static Border paddingBorder(int v, int h) {
        return BorderFactory.createEmptyBorder(v, h, v, h);
    }

    // Styled button
    public static JButton primaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setFont(FONT_BUTTON);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        return btn;
    }

    public static JButton dangerButton(String text) {
        JButton btn = primaryButton(text);
        btn.setBackground(DANGER);
        return btn;
    }

    public static JButton successButton(String text) {
        JButton btn = primaryButton(text);
        btn.setBackground(SUCCESS);
        return btn;
    }

    // Styled text field
    public static JTextField styledField(int columns) {
        JTextField field = new JTextField(columns);
        field.setBackground(BG_CARD);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setFont(FONT_BODY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return field;
    }

    public static JPasswordField styledPasswordField(int columns) {
        JPasswordField field = new JPasswordField(columns);
        field.setBackground(BG_CARD);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setFont(FONT_BODY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return field;
    }

    // Styled label
    public static JLabel titleLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }

    public static JLabel subtitleLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_SUBTITLE);
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }

    public static JLabel bodyLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_BODY);
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }

    public static JLabel mutedLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_SMALL);
        lbl.setForeground(TEXT_MUTED);
        return lbl;
    }

    // Styled table
    public static void styleTable(JTable table) {
        table.setBackground(BG_CARD);
        table.setForeground(TEXT_PRIMARY);
        table.setFont(FONT_BODY);
        table.setGridColor(BORDER_COLOR);
        table.setRowHeight(28);
        table.setSelectionBackground(ACCENT);
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(BG_PANEL);
        table.getTableHeader().setForeground(TEXT_MUTED);
        table.getTableHeader().setFont(FONT_BUTTON);
        table.setFillsViewportHeight(true);
    }

    // Styled scroll pane
    public static JScrollPane styledScroll(Component c) {
        JScrollPane scroll = new JScrollPane(c);
        scroll.setBackground(BG_DARK);
        scroll.getViewport().setBackground(BG_CARD);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        return scroll;
    }

    // Apply dark background to any panel
    public static void stylePanel(JPanel panel) {
        panel.setBackground(BG_PANEL);
    }
}
