package com.rems.ui;

import com.rems.model.User;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static MainFrame instance;
    private JPanel contentPane;

    public MainFrame() {
        instance = this;
        setTitle("REMS - Real Estate Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(UITheme.BG_DARK);
        setContentPane(contentPane);

        showLogin();
        setVisible(true);
    }

    public static MainFrame getInstance() {
        return instance;
    }

    public void showLogin() {
        switchPanel(new LoginPanel());
    }

    public void showDashboard(User user) {
        switch (user.getRole()) {
            case TENANT -> switchPanel(new TenantDashboard(user));
            case AGENT  -> switchPanel(new AgentDashboard(user));
            case ADMIN  -> switchPanel(new AdminDashboard(user));
        }
    }

    public void switchPanel(JPanel panel) {
        contentPane.removeAll();
        contentPane.add(panel, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }
}
