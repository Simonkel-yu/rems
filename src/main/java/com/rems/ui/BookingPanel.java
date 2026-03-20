package com.rems.ui;

import com.rems.controller.BookingController;
import com.rems.controller.UnitController;
import com.rems.dto.AppointmentRequestDTO;
import com.rems.model.Unit;
import com.rems.model.User;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingPanel extends JPanel {

    private final BookingController bookingController = new BookingController();
    private final UnitController unitController = new UnitController();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public BookingPanel(User user) {
        setLayout(new GridBagLayout());
        setBackground(UITheme.BG_DARK);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UITheme.BG_PANEL);
        card.setBorder(UITheme.paddingBorder(30, 40));
        card.setMaximumSize(new Dimension(500, 600));

        card.add(UITheme.titleLabel("Book a Viewing"));
        card.add(Box.createVerticalStrut(20));

        // Unit ID
        card.add(UITheme.bodyLabel("Unit ID"));
        card.add(Box.createVerticalStrut(6));
        JTextField unitField = UITheme.styledField(20);
        card.add(unitField);
        card.add(Box.createVerticalStrut(14));

        // Agent ID
        card.add(UITheme.bodyLabel("Agent ID"));
        card.add(Box.createVerticalStrut(6));
        JTextField agentField = UITheme.styledField(20);
        card.add(agentField);
        card.add(Box.createVerticalStrut(14));

        // Start time
        card.add(UITheme.bodyLabel("Start Time (yyyy-MM-dd HH:mm)"));
        card.add(Box.createVerticalStrut(6));
        JTextField startField = UITheme.styledField(20);
        startField.setText("2026-04-01 10:00");
        card.add(startField);
        card.add(Box.createVerticalStrut(14));

        // End time
        card.add(UITheme.bodyLabel("End Time (yyyy-MM-dd HH:mm)"));
        card.add(Box.createVerticalStrut(6));
        JTextField endField = UITheme.styledField(20);
        endField.setText("2026-04-01 11:00");
        card.add(endField);
        card.add(Box.createVerticalStrut(20));

        // Status label
        JLabel statusLbl = UITheme.mutedLabel(" ");
        statusLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(statusLbl);
        card.add(Box.createVerticalStrut(10));

        // Book button
        JButton bookBtn = UITheme.primaryButton("Request Viewing");
        bookBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookBtn.setMaximumSize(new Dimension(300, 38));
        card.add(bookBtn);

        bookBtn.addActionListener(e -> {
            try {
                AppointmentRequestDTO dto = new AppointmentRequestDTO(
                    Integer.parseInt(unitField.getText().trim()),
                    user.getUserId(),
                    Integer.parseInt(agentField.getText().trim()),
                    LocalDateTime.parse(startField.getText().trim(), FMT),
                    LocalDateTime.parse(endField.getText().trim(), FMT)
                );
                bookingController.requestViewing(dto);
                statusLbl.setForeground(UITheme.SUCCESS);
                statusLbl.setText("✓ Viewing booked successfully!");
            } catch (Exception ex) {
                statusLbl.setForeground(UITheme.DANGER);
                statusLbl.setText("✗ " + ex.getMessage());
            }
        });

        add(card);
    }
}
