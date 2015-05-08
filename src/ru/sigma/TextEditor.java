package ru.sigma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


/**
 * Created by asklykova on 23.04.2015.
 */
public class TextEditor {
    final JFrame mainFrame = new JFrame("Text editor");
    private JButton exitButton;
    private JButton clearButton;
    private JButton zoomInButton;
    private JButton zoomOutButton;
    private JButton yesButton;
    private JButton noButton;
    private File lastFile = null;
    private JTextArea textArea;

    public void buildExitConfirm() {
        final JFrame confirmExitFrame = new JFrame("");
        confirmExitFrame.setBounds(300, 300, 150, 100);

        yesButton = new JButton("Yes");
        noButton = new JButton("No");

        JLabel label = new JLabel("Are you sure?");
        JPanel panel = new JPanel();

        FlowLayout flow = new FlowLayout();
        panel.setLayout(flow);
        panel.add(yesButton);
        panel.add(noButton);
        confirmExitFrame.add(BorderLayout.CENTER, label);
        confirmExitFrame.add(BorderLayout.SOUTH, panel);

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmExitFrame.setVisible(false);
                ;
            }
        });


        confirmExitFrame.setVisible(true);

    }


    public void buildGUI() {

        mainFrame.setBounds(200, 200, 300, 250);

        JPanel panel = new JPanel();
        JPanel zoomPanel = new JPanel();

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridLayout grid = new GridLayout(1, 2);
        GridLayout zoomGrid = new GridLayout(2, 1);

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("Save as");
        JMenuItem newItem = new JMenuItem("New");


        exitButton = new JButton("EXIT");
        clearButton = new JButton("CLEAR");
        zoomInButton = new JButton("+");
        zoomOutButton = new JButton("-");
        textArea = new JTextArea("");

        JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        textArea.setLineWrap(true);


        clearButton.addActionListener(new ClearButtonActionListener());
        exitButton.addActionListener(new ExitButtonActionListener());
        zoomInButton.addActionListener(new ZoomInButtonActionListener());
        zoomOutButton.addActionListener(new ZoomOutButtonActionListener());


        mainFrame.add(BorderLayout.CENTER, scrollPane);
        mainFrame.add(BorderLayout.SOUTH, panel);
        mainFrame.add(BorderLayout.EAST, zoomPanel);
        panel.setLayout(grid);
        zoomPanel.setLayout(zoomGrid);


        panel.add(clearButton);
        panel.add(exitButton);

        menuBar.add(file);

        mainFrame.setJMenuBar(menuBar);

        file.add(openItem);
        file.add(saveItem);
        file.add(saveAsItem);
       // file.add(newItem);

       /* newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((textArea.getText())!= null) saveAsAction();
                else {
                    openAction();
                }
            }
        });*/


        saveItem.addActionListener(new ActionListener() {
                                       @Override
                                       public void actionPerformed(ActionEvent e) {
                                           if (lastFile == null) saveAsAction();

                                           else {
                                               try {

                                                   PrintWriter writer = new PrintWriter(lastFile);

                                                   writer.println(textArea.getText());
                                                   writer.flush();
                                                   writer.close();


                                               } catch (FileNotFoundException e1) {
                                                   e1.printStackTrace();
                                               }

                                           }
                                       }
                                   }

        );

        saveAsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAsAction();
            }
        });

        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAction();
            }
        });

        mainFrame.setVisible(true);
    }
private  void openAction(){
    JFileChooser chooser = new JFileChooser();
    int returnValue = chooser.showOpenDialog(mainFrame);

    if (returnValue == JFileChooser.APPROVE_OPTION) {
        File file = chooser.getSelectedFile();
        System.out.println(file.getAbsolutePath());

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file)));
            String readingLine;
            while ((readingLine = reader.readLine()) != null) {

                textArea.setText(textArea.getText() + readingLine + "\n");

            }
            reader.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        lastFile = file;

    }
}
    private void saveAsAction() {
        JFileChooser chooser = new JFileChooser();
        int returnValue = chooser.showOpenDialog(mainFrame);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            //System.out.println(file.getAbsolutePath());

            try {


                PrintWriter writer = new PrintWriter(file);

                writer.println(textArea.getText());

                writer.flush();
                writer.close();


            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

        }
    }

    private class ClearButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.setText("");
        }
    }

    private class ExitButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            buildExitConfirm();
        }
    }

    private class ZoomInButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text;
            int size;

        }


    }


    private class ZoomOutButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

}