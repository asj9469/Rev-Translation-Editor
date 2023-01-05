package asj9469.CleanerRevTool;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.undo.*;

public class BackgroundMethods {

    //method to perform undo & redo
    public static void undoManager(JTextArea textArea){
        final UndoManager undoManager = new UndoManager(); //instantiate an UndoManager
        Document doc = textArea.getDocument();  //instantiate a Document class of the txtArea
        
        doc.addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent evt) {
                undoManager.addEdit(evt.getEdit());
            }
        });
        
        //undo text change
        textArea.getActionMap().put("Undo", new AbstractAction("Undo") {
            public void actionPerformed(ActionEvent evt) {
                try {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                } catch (CannotUndoException e) {
                }
            }
        });
        
        //redo action
        textArea.getActionMap().put("Redo", new AbstractAction("Redo"){
            public void actionPerformed(ActionEvent evt){
                try{
                    if(undoManager.canRedo()){
                        undoManager.redo();
                    }
                }catch(CannotUndoException e){
                    
                }
            }
        });

        //assign keystrokes
        textArea.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
        textArea.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");
    }
    
    //method to highlight the words
    public static void highlight(JTextArea textArea, String wordToFind){
        
        HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(new Color(112, 128, 144));
        
        try{
            Highlighter highlight = textArea.getHighlighter();
            Document doc = textArea.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;
            
            while((pos = text.toUpperCase().indexOf(wordToFind.toUpperCase(),pos))>=0){
                highlight.addHighlight(pos, pos + wordToFind.length(), painter);
                pos += wordToFind.length();
            }
        }catch(BadLocationException e){
            
        }
        
            
    }
    
    public static JComponent createContent(JPanel contentPane) {
        
        final JWebBrowser webBrowser = new JWebBrowser();
        Dimension preferredSize = new Dimension(819, 434);
        webBrowser.setPreferredSize(preferredSize);
        webBrowser.navigate("http://www.google.com");
        contentPane.add(webBrowser, BorderLayout.CENTER);

//        // Create an additional bar allowing to show/hide the menu bar of the web browser.
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));
//        JCheckBox menuBarCheckBox = new JCheckBox("Menu Bar", webBrowser.isMenuBarVisible());
//        menuBarCheckBox.addItemListener(new ItemListener() {
//            public void itemStateChanged(ItemEvent e) {
//                webBrowser.setMenuBarVisible(e.getStateChange() == ItemEvent.SELECTED);
//            }
//        });
//        buttonPanel.add(menuBarCheckBox);
//        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        return contentPane;
    }
    
    
    public static void openBrowser(JTabbedPane tabbedPane,JButton button){
        
        JLabel browserLabel = new JLabel("Browser");
        JPanel newBrowserPanel = new JPanel(new BorderLayout());
        
        
//        tabbedPane.add("Browser", browserPanel);
//        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, browserLabel);
        createContent(newBrowserPanel);
//        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, button);
    }
        
}