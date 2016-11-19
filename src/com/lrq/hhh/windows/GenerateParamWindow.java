package com.lrq.hhh.windows;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
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
    private Project project;
    private PsiClass psiClass;

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


        project = anActionEvent.getData(PlatformDataKeys.PROJECT);
      psiClass = getPsiClassFromContext(anActionEvent);

        sure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {

                    TodoActionUtil todoActionUtil = new TodoActionUtil();


                    if (!"".equals(textep.getText().trim().toString())) {

                        List<FieldEntity> fieldslist = ParseUtils.parseString(textep.getText().trim().toString());

                        if (fieldslist == null || fieldslist.size() == 0) {
                    /*    tipslabel.setVisible(true);
                        tipslabel.setText("参数格式不对，解析错误！");*/
                            GenerateParamWindow.this.setlabletextvisbile(true, "参数格式不对，解析错误！");
                            return;
                        }

                        todoActionUtil.ActiontoDo2(project,psiClass, textep.getText().trim().toString(),GenerateParamWindow.this);
                       // dispose();
                    } else {
                        GenerateParamWindow.this.setlabletextvisbile(true, "参数不能为空");
                    }
                }catch (Throwable ex)
                {

                }



            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });
    }

    @Override
    public void setvisiblewindow(boolean operator) {


         dispose();


    }
    /**
     * 获取当前焦点下的类
     * @param e
     * @return
     */
    private PsiClass getPsiClassFromContext(AnActionEvent e) {

        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);

        if (psiFile == null || editor == null) {
            return null;
        }

        int offset = editor.getCaretModel().getOffset();
        PsiElement element = psiFile.findElementAt(offset);

        return PsiTreeUtil.getParentOfType(element, PsiClass.class);
    }

    @Override
    public void setlabletextvisbile(boolean operator, String tips) {

        tipslabel.setText(tips);
        tipslabel.setVisible(operator);

    }
}


