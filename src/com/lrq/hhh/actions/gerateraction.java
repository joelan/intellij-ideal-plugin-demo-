package com.lrq.hhh.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.lrq.hhh.windows.createmethodWindow;

/**
 *  gerateraction菜单项下的操作
 */
public class gerateraction extends AnAction {


    public void actionPerformed(AnActionEvent anActionEvent) {
        // TODO: insert action logic here
      //  Messages.showMessageDialog("Hello World sdfsdfsdfsdsdfsd!", "Information", Messages.getInformationIcon());

       // ActiontoDo(anActionEvent);

        createmethodWindow windowdialog=new createmethodWindow(anActionEvent);

        windowdialog.setSize(600, 400);
        windowdialog.setLocationRelativeTo(null);
        windowdialog.setVisible(true);


    }


}
