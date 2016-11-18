package com.lrq.hhh.windows;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.lrq.hhh.entity.FieldEntity;
import com.lrq.hhh.interfacepackge.Operation;
import com.lrq.hhh.utils.ParseUtils;
import com.lrq.hhh.utils.TodoActionUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */
public class GenerateParamWindow  extends JFrame implements Operation {


    AnActionEvent anActionEvent;

    private JPanel panel1;
    private JButton cancel;
    private JButton sure;
    private JLabel tipslabel;
    private JTextPane textep;

    public GenerateParamWindow(AnActionEvent anActionEvent)
    {

        setContentPane(panel1);
        setTitle("GenerateParam");
        this.anActionEvent=anActionEvent;
     /*   JScrollPane scroll = new JScrollPane(MyArea);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);*/
        textep.requestFocus(true);

        init();
    }

    public  void init()
    {
        sure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                TodoActionUtil todoActionUtil = new TodoActionUtil();



                if (!"".equals(textep.getText().trim().toString()))
                {

                    List<FieldEntity> fieldslist= ParseUtils.parseString(textep.getText().trim().toString());

                    if(fieldslist==null||fieldslist.size()==0)
                    {
                    /*    tipslabel.setVisible(true);
                        tipslabel.setText("参数格式不对，解析错误！");*/
                        GenerateParamWindow.this.setlabletextvisbile(true,"参数格式不对，解析错误！");
                    return;
                    }

                    todoActionUtil.ActiontoDo2(anActionEvent, textep.getText().trim().toString());
                    GenerateParamWindow.this.setVisible(false);
                }
                else {
                    GenerateParamWindow.this.setlabletextvisbile(true, "参数不能为空");
                }

            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GenerateParamWindow.this.setVisible(false);
            }
        });
    }

    @Override
    public void setvisiblewindow(boolean operator) {


         dispose();


    }

    @Override
    public void setlabletextvisbile(boolean operator, String tips) {

        tipslabel.setText(tips);
        tipslabel.setVisible(operator);

    }
}


