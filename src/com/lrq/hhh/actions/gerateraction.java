package com.lrq.hhh.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.lrq.hhh.windows.GenerateParamWindow;
import com.lrq.hhh.windows.createmethodWindow;

/**
 *  gerateraction菜单项下的操作
 */
public class gerateraction extends AnAction {


    public void actionPerformed(AnActionEvent anActionEvent) {
        // TODO: insert action logic here
      //  Messages.showMessageDialog("Hello World sdfsdfsdfsdsdfsd!", "Information", Messages.getInformationIcon());

       // ActiontoDo(anActionEvent);

        GenerateParamWindow windowdialog=new GenerateParamWindow(anActionEvent);

        windowdialog.setSize(800, 400);
        windowdialog.setAlwaysOnTop(false);
        windowdialog.setLocationRelativeTo(null);
        windowdialog.setVisible(true);


    }


}
