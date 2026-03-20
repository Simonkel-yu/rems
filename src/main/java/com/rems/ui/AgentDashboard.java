package com.rems.ui;

import com.rems.controller.BookingController;
import com.rems.model.Appointment;
import com.rems.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AgentDashboard extends JPanel {

    private final BookingController bookingController = new BookingController();

    public AgentDashboard(User user) {
        setLayout(new BorderLayout());
        setBackground(UITheme.BG_DARK);

        // Navbar
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(UITheme.BG_PANEL);
        navbar.setBorder(UITheme.paddingBorder(12, 20));
        navbar.add(UITheme.subtitleLabel("🏢 REMS — Agent Dashboard"), BorderLayout.WEST);
        JLabel userInfo = UITheme.mutedLabel("Agent: " + user.getFullName());
        JButton logout = UITheme.dangerButton("Logout");
        logout.addActionListener(e -> MainFrame.getInstance().showLogin());
        navbar.add(userInfo, BorderLayout.CENTER);
        navbar.add(logout, BorderLayout.EAST);
        add(navbar, BorderLayout.NORTH);

        // Content
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UITheme.BG_DARK);
        content.setBorder(UITheme.paddingBorder(20, 20));

        JLabel title = UITheme.titleLabel("My Appointments");
        content.add(title, BorderLayout.NORTH);

        // Table
        String[] cols = {"ID", "Unit", "Tenant", "Start Time", "End Time", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(model);
        UITheme.styleTable(table);

        try {
            List<Appointment> appointments = bookingController.getViewingsForAgent(user.getUserId());
            for (Appointment a : appointments) {
                model.addRow(new Object[]{
                    a.getApptId(),
                    a.getUnit().getUnitNumber(),
                    a.getTenant().getFullName(),
                    a.getStartTime(),
                    a.getEndTime(),
                    a.getStatus()
                });
            }
        } catch (Exception e) {
            model.addRow(new Object[]{"Error loading appointments", "", "", "", "", ""});
        }

        content.add(UITheme.styledScroll(table), BorderLayout.CENTER);

        // Also show unit search for agents
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(UITheme.BG_DARK);
        bottom.setBorder(UITheme.paddingBorder(10, 0));
        bottom.add(UITheme.subtitleLabel("Unit Inventory"), BorderLayout.NORTH);
        bottom.add(new UnitSearchPanel(user), BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, content, bottom);
        split.setDividerLocation(350);
        split.setBackground(UITheme.BG_DARK);
        add(split, BorderLayout.CENTER);
    }
}
