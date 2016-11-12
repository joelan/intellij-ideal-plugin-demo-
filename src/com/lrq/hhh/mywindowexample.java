package com.lrq.hhh;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import javax.swing.*;

/**
 * Created by Administrator on 2016/11/12.
 */
public class mywindowexample implements ToolWindowFactory {
    private JTextField textField1;
    private JTextField textField2;
    private JPanel mcontailjpanel;

    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {


        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(mcontailjpanel, "", false);
        toolWindow.getContentManager().addContent(content);
    }


}
