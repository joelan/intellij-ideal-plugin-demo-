package com.lrq.hhh.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;

/**
 * Action操作类
 */
public class TodoActionUtil {

    /**
     * 操作
     * @param anActionEvent 动作事件context
     * @param methodname 方法名
     */
    public void ActiontoDo(AnActionEvent anActionEvent,String methodname) {

        final Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);


        /**
         * 获取工程名称
         *    String directory=  currentEditorFile.getContainingDirectory().getName();
         */


        /**
         * 获取当前焦点下的文件对象（两种方法）
         *   1. PsiFile psiFile = anActionEvent.getData(LangDataKeys.PSI_FILE);
         *
         *  2. PsiFile currentEditorFile = PsiUtilBase.getPsiFileInEditor(editor, project);
         *
         *
         */


        /**
         * 获取当前焦点下的类
         */
        final PsiClass psiClass = getPsiClassFromContext(anActionEvent);

        PsiElementFactory elementFactory=  JavaPsiFacade.getElementFactory(project);

        final PsiMethod psiMethod= elementFactory.createMethodFromText(generatemethod(PsiType.VOID, methodname), psiClass);


        WriteCommandAction.runWriteCommandAction(project, new Runnable() {
            @Override
            public void run() {

                JavaCodeStyleManager javaCodeStyleManager = JavaCodeStyleManager.getInstance(project);

                /**
                 * 格式化代码和添加方法体代码到类对象的最后位置
                 */
                javaCodeStyleManager.shortenClassReferences(psiClass.addBefore(psiMethod, psiClass.getLastChild()));
                //Messages.showMessageDialog("2", "Information", Messages.getInformationIcon());

                /**
                 * 导入类
                 */
                javaCodeStyleManager.optimizeImports(psiClass.getContainingFile());


               // Messages.showMessageDialog("已成功生成方法", "提示", Messages.getInformationIcon());

            }
        });
    }

    /**
     * 生成方法
     * @param type  方法类型
     * @param metodname 方法名
     * @return
     */
    private String generatemethod(PsiType type,String metodname) {

        StringBuilder sb = new StringBuilder("public "+type.getCanonicalText(false)+ "  "+metodname+"(){" +
                "Log.i(\"mymetodlog\",\"mymetholog msg is show in log\");}");

        return sb.toString();
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
}
