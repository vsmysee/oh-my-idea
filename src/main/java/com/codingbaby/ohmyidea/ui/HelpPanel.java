package com.codingbaby.ohmyidea.ui;

import com.intellij.ui.table.JBTable;
import com.intellij.util.containers.HashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Map;

public class HelpPanel extends JPanel {

    public HelpPanel(Map<String,String> data) {
        JBTable myTable = new JBTable();
        final DefaultTableModel model = new DefaultTableModel(createTableModel(new HashMap<>()), new Object[]{"ShortKey", "Description"}) {
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };

        model.setDataVector(createTableModel(data), new Object[] {"ShortKey", "Description"});

        myTable.setModel(model);
        myTable.setShowVerticalLines(true);
        myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(myTable);
    }

    private static Object[][] createTableModel(Map<String, String> model) {
        Object[][] result = new Object[model.size()][2];
        int index = 0;
        for (final String name : model.keySet()) {
            String value = model.get(name);
            if (value == null) {
                value = "";
            }
            result[index][0] = name;
            result[index][1] = value;
            index++;
        }
        return result;
    }


}
