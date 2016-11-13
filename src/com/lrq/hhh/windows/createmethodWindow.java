package com.lrq.hhh.windows;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.lrq.hhh.utils.TodoActionUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 生产方法操作窗口体
 */
public class createmethodWindow  extends JFrame {

    private JPanel myJpanel;
    private JButton cancel;
    private JButton sureok;
    private JTextField textField1;
    AnActionEvent event;
    public  createmethodWindow(AnActionEvent anActionEvent)
    {


        setContentPane(myJpanel);
        this.event=anActionEvent;
        this.setAlwaysOnTop(true);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createmethodWindow.this.setVisible(false);
            }
        });

        sureok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TodoActionUtil util=new TodoActionUtil();

                if("".equals(textField1.getText().toString().trim().toString()))
                util.ActiontoDo(event,"Hello_to_Mainactivity");
                else
                {
                    util.ActiontoDo(event,textField1.getText().toString().trim().toString());
                }

                createmethodWindow.this.setVisible(false);
            }
        });

    }



}
