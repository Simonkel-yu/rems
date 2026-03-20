package com.rems.ui;

import com.rems.model.User;
import javax.swing.*;
import java.awt.*;

public class TenantDashboard extends JPanel {

    public TenantDashboard(User user) {
        setLayout(new BorderLayout());
        setBackground(UITheme.BG_DARK);

        // Top navbar
        JPanel navbar = buildNavbar(user);
        add(navbar, BorderLayout.NORTH);

        // Sidebar + content
        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(UITheme.BG_DARK);

        JPanel sidebar = buildSidebar();
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UITheme.BG_DARK);
        content.setBorder(UITheme.paddingBorder(20, 20));

        // Default view
        showPanel(content, new UnitSearchPanel(user));

        // Sidebar buttons
        JButton[] buttons = (JButton[]) sidebar.getClientProperty("buttons");
        buttons[0].addActionListener(e -> showPanel(content, new UnitSearchPanel(user)));
        buttons[1].addActionListener(e -> showPanel(content, new BookingPanel(user)));
        buttons[2].addActionListener(e -> showPanel(content, new InvoicePanel(user)));
        buttons[3].addActionListener(e -> showPanel(content, new MaintenancePanel(user)));

        body.add(sidebar, BorderLayout.WEST);
        body.add(content, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);
    }

    private JPanel buildNavbar(User user) {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(UITheme.BG_PANEL);
        nav.setBorder(UITheme.paddingBorder(12, 20));

        JLabel logo = UITheme.subtitleLabel("REMS");
        JLabel userInfo = UITheme.mutedLabel("Logged in as: " + user.getFullName() + " (Tenant)");

        JButton logout = UITheme.dangerButton("Logout");
        logout.addActionListener(e -> MainFrame.getInstance().showLogin());

        nav.add(logo, BorderLayout.WEST);
        nav.add(userInfo, BorderLayout.CENTER);
        nav.add(logout, BorderLayout.EAST);
        return nav;
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UITheme.BG_PANEL);
        sidebar.setBorder(UITheme.paddingBorder(20, 10));
        sidebar.setPreferredSize(new Dimension(180, 0));

        String[] labels = {"Browse Units", "Book Viewing", "My Invoices", "Maintenance"};
        JButton[] buttons = new JButton[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JButton btn = UITheme.primaryButton(labels[i]);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(160, 38));
            btn.setBackground(UITheme.BG_CARD);
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(10));
            buttons[i] = btn;
        }

        sidebar.putClientProperty("buttons", buttons);
        return sidebar;
    }

    private void showPanel(JPanel container, JPanel panel) {
        container.removeAll();
        container.add(panel, BorderLayout.CENTER);
        container.revalidate();
        container.repaint();
    }
}
