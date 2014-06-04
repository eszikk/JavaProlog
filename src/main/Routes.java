/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import view.CityConnPanel;
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
    private static final String tab3Title = "City Connections";


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
        JPanel tab1 = new JPanel(new BorderLayout());
        tab1.add(new RoutePanel(),BorderLayout.WEST);
        tab1.add(new MapPanel(),BorderLayout.EAST);
        
        
        //Tab2
        JPanel tab2 = new JPanel(new BorderLayout());
        
        tab2.add(new CityPanel(),BorderLayout.WEST);
        tab2.add(new MapPanel(),BorderLayout.EAST);
        
        //Tab3
        JPanel tab3 = new JPanel(new BorderLayout());
        
        tab3.add(new CityConnPanel(),BorderLayout.WEST);
        tab3.add(new MapPanel(),BorderLayout.EAST);
        
        
        JTabbedPane tabPane = new JTabbedPane(JTabbedPane.TOP);
        tabPane.addTab(tab1Title, tab1);
        tabPane.addTab(tab2Title, tab2);
        tabPane.addTab(tab3Title, tab3);
        
        
        
               
        mainFrame.add(tabPane);
        mainFrame.setVisible(true);
    }

}
