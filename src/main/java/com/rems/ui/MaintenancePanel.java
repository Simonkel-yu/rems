package com.rems.ui;

import com.rems.controller.MaintenanceController;
import com.rems.model.MaintenanceRequest;
import com.rems.model.User;
import com.rems.model.enums.MaintenancePriority;
import com.rems.model.enums.MaintenanceStatus;
import com.rems.model.enums.UserRole;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MaintenancePanel extends JPanel {

    private final MaintenanceController maintenanceController = new MaintenanceController();
    private DefaultTableModel tableModel;

    public MaintenancePanel(User user) {
        setLayout(new BorderLayout(0, 12));
        setBackground(UITheme.BG_DARK);

        add(UITheme.titleLabel(user.getRole() == UserRole.ADMIN
                ? "Maintenance Queue" : "Report Maintenance Issue"), BorderLayout.NORTH);

        // Table
        String[] cols = {"Ticket ID", "Unit", "Tenant", "Description", "Priority", "Status", "Chargeable"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(tableModel);
        UITheme.styleTable(table);

        // Submit form (tenant) or full queue (admin)
        if (user.getRole() == UserRole.TENANT) {
            JPanel form = buildSubmitForm(user);
            JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                    form, UITheme.styledScroll(table));
            split.setDividerLocation(220);
            split.setBackground(UITheme.BG_DARK);
            add(split, BorderLayout.CENTER);
            loadForTenant(user);
        } else {
            add(UITheme.styledScroll(table), BorderLayout.CENTER);

            // Admin controls
            JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
            controls.setBackground(UITheme.BG_DARK);
            JTextField ticketField = UITheme.styledField(5);
            JButton closeBtn = UITheme.successButton("Close Ticket");
            JLabel statusLbl = UITheme.mutedLabel(" ");

            closeBtn.addActionListener(e -> {
                try {
                    maintenanceController.updateStatus(
                        Integer.parseInt(ticketField.getText().trim()),
                        MaintenanceStatus.CLOSED);
                    statusLbl.setForeground(UITheme.SUCCESS);
                    statusLbl.setText("✓ Ticket closed");
                    loadQueue();
                } catch (Exception ex) {
                    statusLbl.setForeground(UITheme.DANGER);
                    statusLbl.setText("✗ " + ex.getMessage());
                }
            });

            controls.add(UITheme.bodyLabel("Ticket ID:"));
            controls.add(ticketField);
            controls.add(closeBtn);
            controls.add(statusLbl);
            add(controls, BorderLayout.SOUTH);
            loadQueue();
        }
    }

    private JPanel buildSubmitForm(User user) {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(UITheme.BG_PANEL);
        form.setBorder(UITheme.paddingBorder(16, 20));

        form.add(UITheme.subtitleLabel("Submit a Request"));
        form.add(Box.createVerticalStrut(12));

        JTextField unitField = UITheme.styledField(20);
        JTextField descField = UITheme.styledField(20);
        JComboBox<MaintenancePriority> priorityBox =
                new JComboBox<>(MaintenancePriority.values());
        priorityBox.setBackground(UITheme.BG_CARD);
        priorityBox.setForeground(UITheme.TEXT_PRIMARY);
        priorityBox.setFont(UITheme.FONT_BODY);

        JLabel statusLbl = UITheme.mutedLabel(" ");
        statusLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton submitBtn = UITheme.primaryButton("Submit Request");

        submitBtn.addActionListener(e -> {
            try {
                maintenanceController.submitRequest(
                    Integer.parseInt(unitField.getText().trim()),
                    user.getUserId(),
                    descField.getText().trim(),
                    (MaintenancePriority) priorityBox.getSelectedItem()
                );
                statusLbl.setForeground(UITheme.SUCCESS);
                statusLbl.setText("✓ Request submitted!");
                loadForTenant(user);
            } catch (Exception ex) {
                statusLbl.setForeground(UITheme.DANGER);
                statusLbl.setText("✗ " + ex.getMessage());
            }
        });

        form.add(UITheme.bodyLabel("Unit ID:")); form.add(Box.createVerticalStrut(4));
        form.add(unitField); form.add(Box.createVerticalStrut(10));
        form.add(UITheme.bodyLabel("Description:")); form.add(Box.createVerticalStrut(4));
        form.add(descField); form.add(Box.createVerticalStrut(10));
        form.add(UITheme.bodyLabel("Priority:")); form.add(Box.createVerticalStrut(4));
        form.add(priorityBox); form.add(Box.createVerticalStrut(12));
        form.add(submitBtn); form.add(Box.createVerticalStrut(8));
        form.add(statusLbl);
        return form;
    }

    private void loadQueue() {
        tableModel.setRowCount(0);
        try {
            List<MaintenanceRequest> queue = maintenanceController.getQueue();
            for (MaintenanceRequest r : queue) {
                tableModel.addRow(new Object[]{
                    r.getTicketId(),
                    r.getUnit().getUnitNumber(),
                    r.getTenant().getFullName(),
                    r.getDescription(),
                    r.getPriority(),
                    r.getStatus(),
                    r.isChargeable() ? "$" + r.getChargeAmount() : "No"
                });
            }
        } catch (Exception e) {
            tableModel.addRow(new Object[]{"Error: " + e.getMessage(), "", "", "", "", "", ""});
        }
    }

    private void loadForTenant(User user) {
        tableModel.setRowCount(0);
        try {
            List<MaintenanceRequest> requests =
                    maintenanceController.getMyRequests(user.getUserId());
            for (MaintenanceRequest r : requests) {
                tableModel.addRow(new Object[]{
                    r.getTicketId(),
                    r.getUnit().getUnitNumber(),
                    r.getTenant().getFullName(),
                    r.getDescription(),
                    r.getPriority(),
                    r.getStatus(),
                    r.isChargeable() ? "$" + r.getChargeAmount() : "No"
                });
            }
        } catch (Exception e) {
            tableModel.addRow(new Object[]{"Error: " + e.getMessage(), "", "", "", "", "", ""});
        }
    }
}
