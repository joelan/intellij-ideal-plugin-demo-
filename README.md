# Intellij ideal 插件开发教程

##一.官方教程
http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started.html


##二.例子
根据下面的格式生成module类的属性和getter和setter
```
| uid |   true |     string | 用户1 被添加的用户ID  |
| firstname |   true |     string | 名字 |
| lastname |   true |     string | 姓  |
```

##三.总结
###1. psifile当前选中文件
```java
 Project project = anActionEvent.getData(PlatformDataKeys.PROJECT); 
 Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR); 
 PsiFile currentEditorFile = PsiUtilBase.getPsiFileInEditor(editor, project);
```

###2.PsiClass 获取当前选中文件类对象，psiclass能获取对象的方法和属性
```java
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
```


###3.生成一个java类文件

```java
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

```


###4.生成一个文件

```java
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
```
###5.生成成员变量
```java
  protected void generateField(PsiElementFactory factory, PsiClass cls, FieldEntity  Field) {

            StringBuilder fieldSb = new StringBuilder();

                //fieldSb.append("\n");

                fieldSb.append("private  ").append(Field.getFielType()).append(" ").append(Field.getFielName()).append(" ; ");

            cls.add(factory.createFieldFromText(fieldSb.toString(), cls));


    }
```


###6.生成getter和settter
````java
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
    
````


##四.备注信息
1.###FieldEntity类
```java
public class FieldEntity {

    String fielType;
    String fielName;

    public String getFielType() {
        return fielType;
    }

    public void setFielType(String fielType) {
        this.fielType = fielType;
    }

    public String getFielName() {
        return fielName;
    }

    public void setFielName(String fielName) {
        this.fielName = fielName;
    }
}
```



