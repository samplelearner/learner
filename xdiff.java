import com.intellij.diff.DiffContentFactory;
import com.intellij.diff.DiffContext;
import com.intellij.diff.DiffDialogHints;
import com.intellij.diff.FrameDiffTool;
import com.intellij.diff.chains.DiffRequestChain;
import com.intellij.diff.chains.DiffRequestProducer;
import com.intellij.diff.contents.DocumentContent;
import com.intellij.diff.impl.DiffSettingsHolder;
import com.intellij.diff.impl.DiffWindow;
import com.intellij.diff.requests.DiffRequest;
import com.intellij.diff.tools.util.DiffDataKeys;
import com.intellij.diff.util.DiffDrawUtil;
import com.intellij.diff.util.DiffUtil;
import com.intellij.icons.AllIcons;
import com.intellij.internal.DiffFiles;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diff.DiffContent;
import com.intellij.openapi.diff.DiffManager;
import com.intellij.openapi.diff.DiffPanel;
import com.intellij.openapi.diff.DiffRequestFactory;
import com.intellij.openapi.diff.actions.DiffActions;
import com.intellij.openapi.diff.impl.settings.DiffOptionsPanel;
import com.intellij.openapi.diff.impl.util.DiffDivider;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.impl.ProjectImpl;
import com.intellij.openapi.ui.WindowWrapper;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vcs.actions.DiffActionExecutor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.text.DiffLog;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocToken;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IconUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.LineSeparator;
import com.intellij.util.diff.Diff;
import com.sun.deploy.util.ConsoleWindow;

import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.nio.charset.Charset;
import java.util.*;
import java.util.List;

import javax.swing.*;

import javax.swing.*;
import javax.swing.plaf.IconUIResource;

/**
 * Created by sreehari.v on 22/05/15.
 */
public class xdiff extends AnAction {
    public void actionPerformed(AnActionEvent e) {

       if( e.getData(PlatformDataKeys.EDITOR)==null)
       {System.out.println("fail ");}
       else {

           System.out.println("success");
           System.out.println(e.getData(PlatformDataKeys.EDITOR));
            }
        System.out.println(com.intellij.openapi.diff.impl.DiffUtil.isDiffEditor(e.getData(PlatformDataKeys.EDITOR)));

        //PsiFile tFile = e.getData(LangDataKeys.PSI_FILE);
        //PsiElement psiElement= tFile.findElementAt(0);
        //System.out.println(psiElement.toString());
         String text="//comment";
       PsiComment comment =null;
        final PsiComment comment1 = PsiElementFactory.SERVICE.getInstance(e.getProject()).createCommentFromText(text, comment);
        System.out.println(comment1.getText());

       final PsiFile tFile = e.getData(LangDataKeys.PSI_FILE);
        int offset = e.getData(PlatformDataKeys.EDITOR).getCaretModel().getOffset();
        PsiElement elementAt = tFile.findElementAt(offset);
        final PsiClass psiClass = PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);
       final Editor editor= e.getData(PlatformDataKeys.EDITOR);
        final TextAttributes texts= new TextAttributes();
        texts.setBackgroundColor(Color.GREEN);
        texts.setEffectType(EffectType.ROUNDED_BOX);
        texts.setEffectColor(Color.RED);
        texts.setFontType(3);
        //editor.getMarkupModel().addLineHighlighter(5, 1000, texts);





       GutterIconRenderer gutterIconRenderer = new GutterIconRenderer() {
           @NotNull
           @Override
           public Icon getIcon() {
               return IconUtil.getAddIcon();
           }

           @Override
           public boolean equals(Object o) {
               return false;
           }

           @Override
           public int hashCode() {
               return 0;
           }

           @Override
           @Nullable
            public String getTooltipText() {
               return "Review Comment";
                  }



           @Nullable
           @Override
             public AnAction getClickAction() {
               AnAction anAction=new AnAction() {
                   @Override
                   public void actionPerformed(AnActionEvent anActionEvent) {
                       System.out.println("hoppe");
                   }
               };
               return anAction;
           }

           @Override
           @Nullable
             public ActionGroup getPopupMenuActions() {

               ActionGroup actionGroup=new ActionGroup() {

                   @NotNull
                   @Override
                   public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent) {
                       AnAction Action[] = new AnAction[3];
                       Action[0]=new AnAction("Action 1","sample",IconUtil.getRemoveIcon()) {
                           @Override
                           public void actionPerformed(AnActionEvent anActionEvent) {
                               System.out.println("a1");
                           }
                       };
                       Action[1]=new AnAction("Action 2","sample",IconUtil.getAnalyzeIcon()) {
                           @Override
                           public void actionPerformed(AnActionEvent anActionEvent) {
                               System.out.println("a2");
                           }
                       };
                       Action[2]=new AnAction("Action 3","sample",IconUtil.getAddPackageIcon()) {
                           @Override
                           public void actionPerformed(AnActionEvent anActionEvent) {
                               System.out.println("a3");
                           }
                       };

                       return Action;
                   }
               };
               return actionGroup;
           }


       };

      RangeHighlighter rangeHighlighter = editor.getMarkupModel().addRangeHighlighter(20,30,1000,texts,HighlighterTargetArea.EXACT_RANGE);
      rangeHighlighter.setGutterIconRenderer(gutterIconRenderer);


         editor.getFoldingModel().runBatchFoldingOperation(new Runnable() {
             @Override
             public void run() {
                 editor.getFoldingModel().addFoldRegion(20, 50, "Review Comment");
             }
         });

       // editor.getMarkupModel().addRangeHighlighter();


        new WriteCommandAction.Simple(e.getProject(),tFile)
        {
            @Override
            protected void run() throws Throwable {
                PsiElement npsiElement = tFile.findElementAt(0);
                psiClass.addBefore(comment1,tFile.findElementAt(20));

            }
        }.execute();



        Document doc = e.getData(PlatformDataKeys.EDITOR).getDocument();
        VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(doc);










        //ed.getDocument().;

        JPanel jPanel= new JPanel();
        jPanel.setBackground(Color.gray);
        Dimension dimension=new Dimension();
        dimension.setSize(300, 200);
        jPanel.setPreferredSize(dimension);
        jPanel.setLayout(new BoxLayout(jPanel, 3));
        System.out.println();
        JLabel jLabel= new JLabel();
        jLabel.setText("Add comment");
        JEditorPane jEditorPane=new JEditorPane();
        JButton jcomment= new JButton();
        jcomment.setText("Comment");
        jcomment.setForeground(Color.BLUE);
        jcomment.setBounds(0, 50, 0, 50);

        jPanel.add(jLabel);
        jPanel.add(jEditorPane);
        jPanel.add(jcomment);
        ActionListener actionListener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("my bane  ");
            }
        };
        jcomment.addActionListener(actionListener);
        JFrame frame = new JFrame("Sample");
        frame.setContentPane(jPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

}
