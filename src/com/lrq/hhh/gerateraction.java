package com.lrq.hhh;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiClassUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import com.intellij.psi.util.PsiUtilBase;

/**
 * Created by Administrator on 2016/11/13.
 */
public class gerateraction extends AnAction {
    public void actionPerformed(AnActionEvent anActionEvent) {
        // TODO: insert action logic here
      //  Messages.showMessageDialog("Hello World sdfsdfsdfsdsdfsd!", "Information", Messages.getInformationIcon());

        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
        PsiFile currentEditorFile = PsiUtilBase.getPsiFileInEditor(editor, project);

         String directory=  currentEditorFile.getContainingDirectory().getName();
        PsiFile psiFile = anActionEvent.getData(LangDataKeys.PSI_FILE);
        PsiClass psiClass = getPsiClassFromContext(anActionEvent);
       // psiClass.finm
        Messages.showMessageDialog("1.|=" + currentEditorFile.getName() + ";" + "2.|=" + psiFile.getName() + ";3.|=" + directory + ";4.|=" + psiClass.getAllMethods()[1].getName(), "Information", Messages.getInformationIcon());




    }

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
