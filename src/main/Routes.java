/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import view.CityPanel;
import view.MainFrame;
import view.MapPanel;
import view.RoutePanel;

/**
 *
 * @author Krisztian
 */
public class Routes {

    private static final String frameTitle = "European flight routes";
    private static final Integer width = 800;
    private static final Integer height = 500;
    private static final Boolean resizable = false;
    private static final String tab1Title = "Flight routes";
    private static final String tab2Title = "Cities";
    private static final String tab3Title = "City Connection";
    private static final String tab4Title = "Flight routes";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        setFrame();
    }

    public static void setFrame() {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setTitle(frameTitle);
        mainFrame.setSize(width, height);
        mainFrame.setResizable(resizable);
        mainFrame.getContentPane().setLayout(new GridLayout(1, 1));
        
        
        //Tab1
        JPanel tab1 = new JPanel();
        tab1.setLayout(new BorderLayout());
        tab1.add(new RoutePanel());
        tab1.add(new MapPanel());
        
        
        //Tab2
        JPanel tab2 = new JPanel();
        tab2.setLayout(new BorderLayout());
        tab2.add(new CityPanel());
        tab2.add(new MapPanel());
        
        
        JTabbedPane tabPane = new JTabbedPane(JTabbedPane.TOP);
        tabPane.addTab(tab1Title, tab1);
        tabPane.addTab(tab2Title, tab2);
        tabPane.addTab(tab3Title, null);
        
        
        
               
        mainFrame.add(tabPane);
        mainFrame.setVisible(true);
    }

}
