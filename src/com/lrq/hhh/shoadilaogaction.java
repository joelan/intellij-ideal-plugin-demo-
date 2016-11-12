package com.lrq.hhh;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;

/**
 * Created by Administrator on 2016/11/12.
 */
public class shoadilaogaction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here

        Messages.showMessageDialog("Hello World sdfsdfsdfsdsdfsd!", "Information", Messages.getInformationIcon());

    }
}
