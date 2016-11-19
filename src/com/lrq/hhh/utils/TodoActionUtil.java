package com.lrq.hhh.utils;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.lang.Language;
import com.intellij.notification.EventLog;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import com.intellij.psi.util.PsiUtilBase;
import com.jetbrains.cidr.execution.debugger.backend.lldb.auto_generated.Model;
import com.lrq.hhh.entity.FieldEntity;
import com.lrq.hhh.interfacepackge.Operation;
import com.lrq.hhh.windows.GenerateParamWindow;
import org.apache.http.util.TextUtils;

import javax.swing.*;
import java.util.List;

/**
 * Action操作类
 */
public class TodoActionUtil {

    Operation operation;
    /**
     * 操作
     * @param anActionEvent 动作事件context
     * @param methodname 方法名
     */
    public void ActiontoDo(final AnActionEvent anActionEvent, final String methodname) {

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




        WriteCommandAction.runWriteCommandAction(project, new Runnable() {
            @Override
            public void run() {


               // 编辑文件
                EditFile(project, methodname, psiClass);

                //新建文件
               // createfile(anActionEvent, project);

                //新建java文件
                 //createjavafile(anActionEvent, project);

               // Messages.showMessageDialog("已成功生成方法", "提示", Messages.getInformationIcon());

            }
        });




    }


    /**
     * @param project
     * @param psiClass
     * @param paramstring
     */
    public void ActiontoDo2(final Project project, final PsiClass psiClass, final String paramstring,Operation operation) {



       // Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);


        this.operation=operation;



        final List<FieldEntity>  fieldslist=ParseUtils.parseString(paramstring);



        WriteCommandAction.runWriteCommandAction(project, new Runnable() {
            @Override
            public void run() {


                try {
                    if(fieldslist==null||fieldslist.size()==0)
                    {
                       /* tipslabel.setText("参数格式不对，解析错误！");
                        tipslabel.setVisible(true);*/
                       TodoActionUtil.this. operation.setlabletextvisbile(true, "参数格式不对，解析错误！");
                        return;
                    }


                    generateFieldAndSetterAndGetter(project, fieldslist, psiClass);

                  //  TodoActionUtil.this.window.setVisible(false);

                    TodoActionUtil.this.operation.setvisiblewindow(false);
                }
                catch (Exception e)
                {

                   /* tipslabel.setText("创建异常！！"+e.getMessage());
                    tipslabel.setVisible(true);*/
                    TodoActionUtil.this. operation.setlabletextvisbile(true, "参数格式不对，解析错误！");
                    TodoActionUtil.this.operation.setvisiblewindow(false);

                 //  e.printStackTrace();


                }


            }
        });



    }

    private void generateFieldAndSetterAndGetter(Project project, List<FieldEntity> fieldslist, PsiClass psiClass) {
        PsiElementFactory elementFactory=  JavaPsiFacade.getElementFactory(project);
        for(int i=0;i<fieldslist.size();i++)
        {
            generateField(elementFactory,psiClass,fieldslist.get(i));
        }

        for(int i=0;i<fieldslist.size();i++)
        {
            createGetAndSetMethod(elementFactory, psiClass, fieldslist.get(i));
        }
    }

    /**
     * 生成属性名
     * @param factory
     * @param cls
     * @param Field
     */
    protected void generateField(PsiElementFactory factory, PsiClass cls, FieldEntity  Field) {

            StringBuilder fieldSb = new StringBuilder();

                //fieldSb.append("\n");

                fieldSb.append("private  ").append(Field.getFielType()).append(" ").append(Field.getFielName()).append(" ; ");

            cls.add(factory.createFieldFromText(fieldSb.toString(), cls));


    }

    protected void createGetAndSetMethod(PsiElementFactory factory, PsiClass cls, FieldEntity field) {
            String fieldName = field.getFielName();
            String typeStr = field.getFielType();
                String methodx = "public ".concat(typeStr).concat(
                        "   get").concat(
                        captureName(fieldName)).concat(
                        "() {   return ").concat(
                        field.getFielName()).concat(" ;} ");
                cls.add(factory.createMethodFromText(methodx, cls));


        String arg = fieldName;

            String method = "public void  set".concat(captureName(fieldName)).concat("( ").concat(typeStr).concat(" ").concat(arg).concat(") {   ");
                method = method.concat("this.").concat(field.getFielName()).concat(" = ").concat(arg).concat(";} ");

            cls.add(factory.createMethodFromText(method, cls));

    }

    public static String captureName(String text) {

        if (text.length() > 0) {
            text = text.substring(0, 1).toUpperCase() + text.substring(1);
        }
        return text;
    }


    /**
     * 生成一个java文件
     * @param anActionEvent
     * @param project
     */
    private  void createjavafile(AnActionEvent anActionEvent, Project project)
    {
        PsiFile psiFilecurrent = anActionEvent.getData(LangDataKeys.PSI_FILE);
        JavaDirectoryService.getInstance().createClass(psiFilecurrent.getContainingDirectory(), "MYjava");
    }

    /**
     * 生产文件
     * @param anActionEvent
     * @param project
     */
    private void createfile(AnActionEvent anActionEvent, Project project) {

        PsiFile psijavaFile=  PsiFileFactory.getInstance(project).createFileFromText("myjavafile."+JavaFileType.DEFAULT_EXTENSION, JavaFileType.INSTANCE, "myjava");
        // PsiFile psixmlFile=  PsiFileFactory.getInstance(project).createFileFromText("myxmlfile", XmlFileType.INSTANCE, "myxml");
        // StdFileTypes.XML

        PsiFile psixmlFile=  PsiFileFactory.getInstance(project).createFileFromText("myxmlfile."+StdFileTypes.XML.getDefaultExtension(), StdFileTypes.XML, "myxml");

        PsiFile psiFilecurrent = anActionEvent.getData(LangDataKeys.PSI_FILE);
        psiFilecurrent.getContainingDirectory().add(psijavaFile);
        psiFilecurrent.getContainingDirectory().add(psixmlFile);
    }

    /**
     * 编辑文件
     * @param project
     * @param methodname
     * @param psiClass
     */
    private void EditFile(Project project, String methodname, PsiClass psiClass) {

        PsiElementFactory elementFactory=  JavaPsiFacade.getElementFactory(project);

        final PsiMethod psiMethod= elementFactory.createMethodFromText(generatemethod(PsiType.VOID, methodname), psiClass);
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
    }

    /**
     * 通过Language创建文件
     *  */

  /*  private static PsiElement generateCPP(Project project, Task task, VirtualFile newTaskFile) {
     VirtualFile parent = newTaskFile.getParent();
     final PsiDirectory psiParent = PsiManager.getInstance(project).findDirectory(parent);
     if (psiParent == null) {
     throw new NotificationException("Couldn't open parent directory as PSI");
     }

     Language objC = Language.findLanguageByID("ObjectiveC");
     if (objC == null) {
     throw new NotificationException("Language not found");
     }

     final PsiFile file = PsiFileFactory.getInstance(project).createFileFromText(
     task.getClassName() + ".cpp",
     objC,
     getTaskContent(project, task.getClassName())
     );
     if (file == null) {
     throw new NotificationException("Couldn't generate file");
     }
     return ApplicationManager.getApplication().runWriteAction(
     new Computable<PsiElement>() {
    @Override
    public PsiElement compute() {
    return psiParent.add(file);
    }
    }
     );

     }*/


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
