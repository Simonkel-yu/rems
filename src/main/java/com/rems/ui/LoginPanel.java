package com.rems.ui;

import com.rems.controller.AuthController;
import com.rems.model.User;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private final AuthController authController = new AuthController();

    public LoginPanel() {
        setLayout(new GridBagLayout());
        setBackground(UITheme.BG_DARK);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UITheme.BG_PANEL);
        card.setBorder(UITheme.paddingBorder(40, 50));
        card.setMaximumSize(new Dimension(400, 500));

        // Title
        JLabel title = UITheme.titleLabel("REMS");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel subtitle = UITheme.mutedLabel("Real Estate Management System");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Fields
        JLabel emailLbl = UITheme.bodyLabel("Email");
        JTextField emailField = UITheme.styledField(20);

        JLabel passLbl = UITheme.bodyLabel("Password");
        JPasswordField passField = UITheme.styledPasswordField(20);

        // Error label
        JLabel errorLbl = UITheme.mutedLabel(" ");
        errorLbl.setForeground(UITheme.DANGER);
        errorLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login button
        JButton loginBtn = UITheme.primaryButton("Login");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(300, 38));

        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passField.getPassword());
            try {
                User user = authController.login(email, password);
                MainFrame.getInstance().showDashboard(user);
            } catch (Exception ex) {
                errorLbl.setText(ex.getMessage());
            }
        });

        // Hint
        JLabel hint = UITheme.mutedLabel("Hint: admin@rems.com / admin123");
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(30));
        card.add(emailLbl);
        card.add(Box.createVerticalStrut(6));
        card.add(emailField);
        card.add(Box.createVerticalStrut(16));
        card.add(passLbl);
        card.add(Box.createVerticalStrut(6));
        card.add(passField);
        card.add(Box.createVerticalStrut(20));
        card.add(errorLbl);
        card.add(Box.createVerticalStrut(10));
        card.add(loginBtn);
        card.add(Box.createVerticalStrut(16));
        card.add(hint);

        add(card);
    }
}
