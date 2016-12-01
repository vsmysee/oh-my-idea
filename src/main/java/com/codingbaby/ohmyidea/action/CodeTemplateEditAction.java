package com.codingbaby.ohmyidea.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.JBLabel;
import com.codingbaby.ohmyidea.script.OhScript;



import javax.swing.*;

/**
 * Created by baby on 16/6/9.
 */

public class CodeTemplateEditAction extends DumbAwareAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject() == null ? ProjectManager.getInstance().getDefaultProject() : e.getProject();
        EditDialog dialog = new EditDialog(project, OhScript.loadContent());
        if (dialog.showAndGet()) {
            String text = dialog.myTextArea.getText();
            OhScript.saveScript(text);
        }
    }

    private static class EditDialog extends DialogWrapper {


        private final JTextArea myTextArea;

        protected EditDialog( Project project, String text) {
            super(project, false);
            myTextArea = new JTextArea(30, 100);
            myTextArea.setText(text);
            setTitle("编辑代码模板");
            init();
        }


        @Override
        protected JComponent createNorthPanel() {
            return new JBLabel("");
        }


        @Override
        protected JComponent createCenterPanel() {
            return ScrollPaneFactory.createScrollPane(myTextArea);
        }


        @Override
        public JComponent getPreferredFocusedComponent() {
            return myTextArea;
        }



    }

}
