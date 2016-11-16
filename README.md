# itellij ideal 插件开发教程

##1.官方教程
http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started.html

1. psifile当前选中文件
 Project project = anActionEvent.getData(PlatformDataKeys.PROJECT); 
 Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR); 
 PsiFile currentEditorFile = PsiUtilBase.getPsiFileInEditor(editor, project);


2.PsiClass 获取当前选中文件类对象，psiclass能获取对象的方法和属性
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



3.生成一个java类文件
/**
* 生成一个java文件
* @param anActionEvent
* @param project
*/
private void createjavafile(AnActionEvent anActionEvent, Project project)
{
PsiFile psiFilecurrent = anActionEvent.getData(LangDataKeys.PSI_FILE);
JavaDirectoryService.getInstance().createClass(psiFilecurrent.getContainingDirectory(), "MYjava");
}




4.生成一个文件
/**
* 生产文件
* @param anActionEvent
* @param project
*/
private void createfile(AnActionEvent anActionEvent, Project project) {
PsiFile psijavaFile= PsiFileFactory.getInstance(project).createFileFromText("myjavafile."+JavaFileType.DEFAULT_EXTENSION, JavaFileType.INSTANCE, "myjava");
// PsiFile psixmlFile= PsiFileFactory.getInstance(project).createFileFromText("myxmlfile", XmlFileType.INSTANCE, "myxml");
// StdFileTypes.XML
PsiFile psixmlFile= PsiFileFactory.getInstance(project).createFileFromText("myxmlfile."+StdFileTypes.XML.getDefaultExtension(), StdFileTypes.XML, "myxml");
PsiFile psiFilecurrent = anActionEvent.getData(LangDataKeys.PSI_FILE);
psiFilecurrent.getContainingDirectory().add(psijavaFile);
psiFilecurrent.getContainingDirectory().add(psixmlFile);
}





