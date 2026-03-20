package com.rems.ui;

import com.rems.controller.UnitController;
import com.rems.model.Unit;
import com.rems.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UnitSearchPanel extends JPanel {

    private final UnitController unitController = new UnitController();
    private DefaultTableModel tableModel;

    public UnitSearchPanel(User user) {
        setLayout(new BorderLayout(0, 12));
        setBackground(UITheme.BG_DARK);

        // Filter bar
        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filters.setBackground(UITheme.BG_DARK);

        JComboBox<String> tierBox = new JComboBox<>(new String[]{"Any Tier", "1", "2", "3", "4"});
        tierBox.setBackground(UITheme.BG_CARD);
        tierBox.setForeground(UITheme.TEXT_PRIMARY);
        tierBox.setFont(UITheme.FONT_BODY);

        JTextField maxRateField = UITheme.styledField(8);
        maxRateField.setToolTipText("Max monthly rate");

        JTextField minSizeField = UITheme.styledField(6);
        minSizeField.setToolTipText("Min size (sqft)");

        JButton searchBtn = UITheme.primaryButton("Search");
        JButton clearBtn  = UITheme.primaryButton("Clear");
        clearBtn.setBackground(UITheme.BG_CARD);

        filters.add(UITheme.bodyLabel("Tier:"));
        filters.add(tierBox);
        filters.add(UITheme.bodyLabel("Max Rate $:"));
        filters.add(maxRateField);
        filters.add(UITheme.bodyLabel("Min Size sqft:"));
        filters.add(minSizeField);
        filters.add(searchBtn);
        filters.add(clearBtn);

        add(filters, BorderLayout.NORTH);

        // Table
        String[] cols = {"ID", "Mall", "Unit #", "Size (sqft)", "Base Rate/mo", "Tier", "Usage", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(tableModel);
        UITheme.styleTable(table);
        add(UITheme.styledScroll(table), BorderLayout.CENTER);

        // Load all on start
        loadUnits(null, null, null, null);

        searchBtn.addActionListener(e -> {
            Integer tier = tierBox.getSelectedIndex() == 0 ? null : tierBox.getSelectedIndex();
            Double maxRate = maxRateField.getText().isEmpty() ? null : Double.parseDouble(maxRateField.getText());
            Double minSize = minSizeField.getText().isEmpty() ? null : Double.parseDouble(minSizeField.getText());
            loadUnits(tier, minSize, null, maxRate);
        });

        clearBtn.addActionListener(e -> {
            tierBox.setSelectedIndex(0);
            maxRateField.setText("");
            minSizeField.setText("");
            loadUnits(null, null, null, null);
        });
    }

    private void loadUnits(Integer tier, Double minSize, Double maxSize, Double maxRate) {
        tableModel.setRowCount(0);
        try {
            List<Unit> units = (tier == null && minSize == null && maxRate == null)
                    ? unitController.getAvailableUnits()
                    : unitController.searchUnits(tier, minSize, maxSize, maxRate);
            for (Unit u : units) {
                tableModel.addRow(new Object[]{
                    u.getUnitId(),
                    u.getMall().getName(),
                    u.getUnitNumber(),
                    u.getSizeSqft(),
                    "$" + u.getBaseRate(),
                    "Tier " + u.getTier(),
                    u.getUsageType(),
                    u.getStatus()
                });
            }
        } catch (Exception e) {
            tableModel.addRow(new Object[]{"Error: " + e.getMessage(), "", "", "", "", "", "", ""});
        }
    }
}
