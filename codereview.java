import com.intellij.diff.tools.util.DiffDataKeys;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorGutter;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.TextAnnotationGutterProvider;
import com.intellij.openapi.editor.colors.ColorKey;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseEventArea;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IconUtil;
import com.thaiopensource.relaxng.edit.TextAnnotation;
import freemarker.core.ReturnInstruction;
import org.apache.xmlbeans.impl.xb.xsdschema.WhiteSpaceDocument;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static org.apache.xmlbeans.impl.xb.xsdschema.WhiteSpaceDocument.*;

/**
 * Created by sreehari.v on 14/05/15.
 */
public class codereview extends AnAction {

    Object threadlis = new Object();
    final String[] s = {null};

        public void panel() {

        JPanel jPanel= new JPanel();
        jPanel.setBackground(Color.gray);
        Dimension dimension=new Dimension();
        dimension.setSize(300, 200);
        jPanel.setPreferredSize(dimension);
        jPanel.setLayout(new BoxLayout(jPanel, 3));
        System.out.println();
        JLabel jLabel= new JLabel();
        jLabel.setText("Add comment");
        final JEditorPane jEditorPane=new JEditorPane();
        JButton jcomment= new JButton();
        jcomment.setText("Comment");
        jcomment.setForeground(Color.BLUE);
        jcomment.setBounds(0, 50, 0, 50);

        jPanel.add(jLabel);
        jPanel.add(jEditorPane);
        jPanel.add(jcomment);
        final JFrame frame = new JFrame("Sample");
        ActionListener actionListener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                 s[0] = jEditorPane.getText();

                synchronized (threadlis) {
                         System.out.println("notifying k");

                    threadlis.notify();

                }
               System.out.println("check pass ");
                frame.setVisible(false);

            }
        };
        jcomment.addActionListener(actionListener);

        frame.setContentPane(jPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        System.out.println("ovvvvver");

        }



    public void actionPerformed(final AnActionEvent e) {

        final int[] k = {0,0};

        System.out.println(e.getPlace());

        final Editor editor = e.getData(PlatformDataKeys.EDITOR);

        final EditorMouseListener m= new EditorMouseListener() {

            @Override
            public void mousePressed(EditorMouseEvent editorMouseEvent) {

            }

            @Override
            public void mouseClicked(EditorMouseEvent ev) {


                JPanel jPanel= new JPanel();
                jPanel.setBackground(Color.gray);
                Dimension dimension=new Dimension();
                dimension.setSize(300, 200);
                jPanel.setPreferredSize(dimension);
                jPanel.setLayout(new BoxLayout(jPanel, 3));
                System.out.println();
                JLabel jLabel= new JLabel();
                jLabel.setText("Add comment");
                final JEditorPane jEditorPane=new JEditorPane();
                JButton jcomment= new JButton();
                jcomment.setText("Comment");
                jcomment.setForeground(Color.BLUE);
                jcomment.setBounds(0, 50, 0, 50);

                jPanel.add(jLabel);
                jPanel.add(jEditorPane);
                jPanel.add(jcomment);
                final JFrame frame = new JFrame("Sample");



                ActionListener actionListener=new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        s[0] = jEditorPane.getText();
                        System.out.println("check pass ");
                        frame.setVisible(false);



                        LogicalPosition lp = editor.getCaretModel().getLogicalPosition();

                        final int offset = editor.logicalPositionToOffset(lp);


                      System.out.println("passss");

                        final String txt=s[0];
                        System.out.println(s[0]);

                        System.out.println(e.getData(PlatformDataKeys.PSI_FILE));

                        System.out.println("printed");

                        System.out.println(e.getData(PlatformDataKeys.EDITOR));
                        System.out.println(e.getData(LangDataKeys.EDITOR));


                    //    final PsiFile tFile = e.getData(LangDataKeys.PSI_FILE);

                       final PsiFile tFile= PsiDocumentManager.getInstance( e.getData(DiffDataKeys.CURRENT_EDITOR).getProject()).getPsiFile(e.getData(DiffDataKeys.CURRENT_EDITOR).getDocument());


                        if(tFile==null)
                            System.out.println("err fnd");
                        else
                        System.out.println(tFile.toString());
                        final PsiElement elementAt = tFile.findElementAt(offset);

                        System.out.println("initial rel");
                        System.out.println(elementAt);
                        System.out.println(offset);
                        System.out.println(elementAt.getPrevSibling());

                        final TextAttributes texts= new TextAttributes();
                        texts.setBackgroundColor(Color.CYAN);
                        texts.setEffectType(EffectType.ROUNDED_BOX);


                        final TextAttributes textsone= new TextAttributes();
                        textsone.setBackgroundColor(Color.green);
                        textsone.setEffectType(EffectType.ROUNDED_BOX);

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


                            @Override
                            @Nullable
                            public ActionGroup getPopupMenuActions() {

                                ActionGroup actionGroup=new ActionGroup() {

                                    @NotNull
                                    @Override
                                    public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent) {
                                        AnAction Action[] = new AnAction[3];
                                        Action[0]=new AnAction("Done","sample",IconUtil.getRemoveIcon()) {
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


                        final PsiClass psiClass = PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);
                        //final Editor editor= e.getData(PlatformDataKeys.EDITOR);


                        PsiComment comment =null;

                        StringBuilder stringBuilder=new StringBuilder();
                        stringBuilder.append("/*");
                        stringBuilder.append("  Review added by : User 1");
                        stringBuilder.append('\n');
                        stringBuilder.append(txt);
                        stringBuilder.append('\n');
                        stringBuilder.append("*/");

                        final PsiComment comment1 = PsiElementFactory.SERVICE.getInstance(e.getProject()).createCommentFromText(stringBuilder.toString(), comment);

                        new WriteCommandAction.Simple(e.getProject(),tFile)
                        {
                            @Override
                            protected void run() throws Throwable {
                                if(psiClass!=null)
                                {    if(!comment1.textMatches("//null"))
                                {
                                    System.out.println("trying to addd ");

                             if(elementAt.getParent()!=null)
                               elementAt.getParent().addBefore(comment1, elementAt);
                             else
                                 System.out.println("parent null error");

                                    System.out.println("addedddd");

                                }}
                                else
                                    System.out.println("null class");

                            }
                        }.execute();


                        int start = comment1.getTextLength();
                        int stop = elementAt.getTextOffset();

                        if(tFile.findElementAt(stop-1)!=null)
                            for( ;(tFile.findElementAt(stop-1).toString()).contentEquals("PsiWhiteSpace");stop-- );

                        final int nstop = stop;
                        final int   nstart = nstop-start;

                        System.out.println("datas");
                        System.out.println(comment1.getTextOffset());
                        System.out.println(comment1.getNavigationElement().getTextOffset());
                        System.out.println(elementAt.getTextOffset());
                        if(elementAt.getPrevSibling()!=null)
                            System.out.println(elementAt.getPrevSibling().getTextOffset());

                        System.out.println(nstart);
                        System.out.println(nstop);


                        RangeHighlighter rangeHighlighter = editor.getMarkupModel().addRangeHighlighter(nstart,nstop,6000,texts, HighlighterTargetArea.EXACT_RANGE);
                        rangeHighlighter.setGutterIconRenderer(gutterIconRenderer);
                        editor.getMarkupModel().addRangeHighlighter(nstart+4, nstart+28, 6000, textsone, HighlighterTargetArea.EXACT_RANGE);

                        editor.getFoldingModel().runBatchFoldingOperation(new Runnable() {
                            @Override
                            public void run() {

                                editor.getFoldingModel().addFoldRegion(nstart + 1, nstop - 1, "Review Comment");
                            }
                        });

                        k[0] =1;

                    }
                };
                jcomment.addActionListener(actionListener);

                frame.setContentPane(jPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                System.out.println("ovvvvver");

                //trier a=new trier();
                //panel();
                //a.start();

            }

            @Override
            public void mouseReleased(EditorMouseEvent editorMouseEvent) {

            }

            @Override
            public void mouseEntered(EditorMouseEvent editorMouseEvent) {

            }

            @Override
            public void mouseExited(EditorMouseEvent editorMouseEvent) {

            }
        };


        class th extends Thread {
            public void run() {

                while (true) {
                    if (k[0] == 1) {
                        editor.removeEditorMouseListener(m);
                        break;
                    } else {
                        k[1] = 0;
                    }
                }
            }
        }
        editor.addEditorMouseListener(m);
        th newthread = new th();
        newthread.start();
    }
}
