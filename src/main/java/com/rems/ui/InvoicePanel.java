package com.rems.ui;

import com.rems.controller.InvoiceController;
import com.rems.controller.LeaseController;
import com.rems.model.Invoice;
import com.rems.model.Lease;
import com.rems.model.User;
import com.rems.model.enums.UserRole;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InvoicePanel extends JPanel {

    private final InvoiceController invoiceController = new InvoiceController();
    private final LeaseController leaseController = new LeaseController();
    private DefaultTableModel tableModel;

    public InvoicePanel(User user) {
        setLayout(new BorderLayout(0, 12));
        setBackground(UITheme.BG_DARK);

        add(UITheme.titleLabel(user.getRole() == UserRole.ADMIN ? "All Invoices" : "My Invoices"), BorderLayout.NORTH);

        String[] cols = {"Invoice ID", "Lease ID", "Period", "Base Rent", "Water", "Electricity", "Waste", "Total", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(tableModel);
        UITheme.styleTable(table);
        add(UITheme.styledScroll(table), BorderLayout.CENTER);

        // Mark as paid button (admin only)
        if (user.getRole() == UserRole.ADMIN) {
            JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
            bottom.setBackground(UITheme.BG_DARK);
            JTextField invoiceIdField = UITheme.styledField(6);
            JButton payBtn = UITheme.successButton("Mark as Paid");
            JLabel statusLbl = UITheme.mutedLabel(" ");

            payBtn.addActionListener(e -> {
                try {
                    invoiceController.markAsPaid(Integer.parseInt(invoiceIdField.getText().trim()));
                    statusLbl.setForeground(UITheme.SUCCESS);
                    statusLbl.setText("✓ Invoice marked as paid");
                    loadInvoices(user);
                } catch (Exception ex) {
                    statusLbl.setForeground(UITheme.DANGER);
                    statusLbl.setText("✗ " + ex.getMessage());
                }
            });

            bottom.add(UITheme.bodyLabel("Invoice ID:"));
            bottom.add(invoiceIdField);
            bottom.add(payBtn);
            bottom.add(statusLbl);
            add(bottom, BorderLayout.SOUTH);
        }

        loadInvoices(user);
    }

    private void loadInvoices(User user) {
        tableModel.setRowCount(0);
        try {
            List<Invoice> invoices;
            if (user.getRole() == UserRole.ADMIN) {
                invoices = invoiceController.getAllInvoices();
            } else {
                invoices = leaseController.getLeasesByTenant(user.getUserId()).stream()
                    .flatMap(l -> invoiceController.getInvoicesForLease(l.getLeaseId()).stream())
                    .toList();
            }
            for (Invoice inv : invoices) {
                tableModel.addRow(new Object[]{
                    inv.getInvoiceId(),
                    inv.getLease().getLeaseId(),
                    inv.getBillingPeriod(),
                    "$" + inv.getBaseRent(),
                    "$" + inv.getWaterCharge(),
                    "$" + inv.getElectricityCharge(),
                    "$" + inv.getWasteCharge(),
                    "$" + inv.getTotalAmount(),
                    inv.getStatus()
                });
            }
        } catch (Exception e) {
            tableModel.addRow(new Object[]{"Error: " + e.getMessage(), "", "", "", "", "", "", "", ""});
        }
    }
}
