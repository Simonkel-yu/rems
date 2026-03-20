package com.rems.ui;

import com.rems.model.User;
import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JPanel {

    public AdminDashboard(User user) {
        setLayout(new BorderLayout());
        setBackground(UITheme.BG_DARK);

        // Navbar
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(UITheme.BG_PANEL);
        navbar.setBorder(UITheme.paddingBorder(12, 20));
        navbar.add(UITheme.subtitleLabel("🏢 REMS — Admin Dashboard"), BorderLayout.WEST);
        JLabel userInfo = UITheme.mutedLabel("Admin: " + user.getFullName());
        JButton logout = UITheme.dangerButton("Logout");
        logout.addActionListener(e -> MainFrame.getInstance().showLogin());
        navbar.add(userInfo, BorderLayout.CENTER);
        navbar.add(logout, BorderLayout.EAST);
        add(navbar, BorderLayout.NORTH);

        // Sidebar
        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(UITheme.BG_DARK);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UITheme.BG_PANEL);
        sidebar.setBorder(UITheme.paddingBorder(20, 10));
        sidebar.setPreferredSize(new Dimension(180, 0));

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UITheme.BG_DARK);
        content.setBorder(UITheme.paddingBorder(20, 20));

        String[] labels = {"🏬 Manage Units", "🔧 Maintenance Queue", "🧾 All Invoices"};
        JPanel[] panels = {
            new UnitSearchPanel(user),
            new MaintenancePanel(user),
            new InvoicePanel(user)
        };

        showPanel(content, panels[0]);

        for (int i = 0; i < labels.length; i++) {
            final int idx = i;
            JButton btn = UITheme.primaryButton(labels[i]);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(160, 38));
            btn.setBackground(UITheme.BG_CARD);
            btn.addActionListener(e -> showPanel(content, panels[idx]));
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(10));
        }

        body.add(sidebar, BorderLayout.WEST);
        body.add(content, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);
    }

    private void showPanel(JPanel container, JPanel panel) {
        container.removeAll();
        container.add(panel, BorderLayout.CENTER);
        container.revalidate();
        container.repaint();
    }
}
