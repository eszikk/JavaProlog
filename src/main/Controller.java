/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dao.FileDao;
import dao.JavaProlog;
import entity.City;
import entity.Route;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import view.MapPanel;

/**
 *
 * @author Krisztian
 */
public class Controller {

    private static String mapImageURL = "C:\\Users\\Krisztian\\Documents\\NetBeansProjects\\Routes\\src\\src\\map.gif";
    private static Image map;
    private static Integer mapWidth = 600;
    private static Integer mapHeight = 445;
    private static Integer mapX = 0;
    private static Integer mapY = 0;
    private static Graphics g;
    private static MapPanel mapPanel;

    /**
     * Draws the map to the panel.
     *
     * @param g
     */
    public static void DrawMap() {
        try {
            map = ImageIO.read(new File(mapImageURL));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.drawImage(map, mapX, mapY, mapWidth, mapHeight, null);

    }

    public static void SetGraphics(Graphics g, MapPanel panel) {
        Controller.g = g;
        Controller.mapPanel = panel;
    }

    /**
     * Get city list from file dao and pass torward.
     *
     * @return
     */
    public static List<City> GetCities() {
        FileDao dao = new FileDao();
        try {
            return dao.GetCities();
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static List<String> GetConnectedCities(City par) {
        JavaProlog prDao = new JavaProlog();
        return prDao.GetConnectedCities(par.getName());
    }

    public static List<Route> GetRoutes(String start, String dest) {
        JavaProlog prDao = new JavaProlog();
        List<Route> routes = prDao.GetRoutes(start, dest);
        return routes;
    }

    public static void DrawRoutes(Route route) {
        Integer X;
        Integer Y;
        Integer r = 10;
        g.setColor(Color.red);

        for (City c : route.getCityList()) {
            X = c.getCoordX() - (r / 2);
            Y = c.getCoordY() - (r / 2);

            g.fillOval(X, Y, r, r);
            g.setFont(new Font("Arial Black", Font.BOLD, 12));
            g.drawString(c.getName(), X, Y);
        }

    }
    //MapPanel.repaint();

}
