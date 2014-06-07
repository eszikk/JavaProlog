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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Krisztian
 */
public class Controller {

    private static final Integer TAB_1 = 0;
    private static final Integer TAB_2 = 1;
    private static final Integer TAB_3 = 2;

    private static final String mapImageURL = "src/data/map.gif";
    private static Image map;
    private static final Integer mapWidth = 600;
    private static final Integer mapHeight = 445;
    private static final Integer mapX = 0;
    private static final Integer mapY = 0;

    private static Route routes;
    private static City cPanel = null;

    /**
     * Draws the map to the panel.
     *
     * @param g MapPanel Graphics Object.
     * @param TAB Witch tab is active.
     */
    public static void Draw(Graphics g, Integer TAB) {
        try {
            map = ImageIO.read(new File(mapImageURL));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.drawImage(map, mapX, mapY, mapWidth, mapHeight, null);

        if (Objects.equals(TAB, TAB_1)) {
            DrawRoutes(g);
        } else if (Objects.equals(TAB, TAB_2)) {
            DrawCity(g);
        } else if (Objects.equals(TAB, TAB_3)) {

        }

    }

    private static void DrawCity(Graphics g) {
        Integer r = 10;
        g.setColor(Color.red);
        g.fillOval(cPanel.getCoordX(), cPanel.getCoordY(), r, r);
        g.setFont(new Font("Arial Black", Font.BOLD, 12));
        g.setColor(Color.BLACK);
        g.drawString(cPanel.getName(), cPanel.getCoordX(), cPanel.getCoordY());

    }

    private static void DrawRoutes(Graphics g) {
        if (routes != null) {
            Integer X;
            Integer Y;
            Integer r = 10;

            if (routes.getCityList().size() == 2) {
                g.setColor(Color.GRAY);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3));
                g2.drawLine(routes.getCityList().get(0).getCoordX(), routes.getCityList().get(0).getCoordY(),
                        routes.getCityList().get(1).getCoordX(), routes.getCityList().get(1).getCoordY());
            } else {
                g.setColor(Color.GRAY);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3));

                for (int i = 0; i < routes.getCityList().size() - 1; i++) {
                    g2.drawLine(routes.getCityList().get(i).getCoordX(), routes.getCityList().get(i).getCoordY(),
                            routes.getCityList().get(i + 1).getCoordX(), routes.getCityList().get(i + 1).getCoordY());
                }

            }

            for (City c : routes.getCityList()) {
                X = c.getCoordX() - (r / 2);
                Y = c.getCoordY() - (r / 2);

                g.setColor(Color.red);
                g.fillOval(X, Y, r, r);
                g.setFont(new Font("Arial Black", Font.BOLD, 12));
                g.setColor(Color.BLACK);
                g.drawString(c.getName(), X, Y);
            }

        }
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
        try {
            JavaProlog prDao = new JavaProlog();
            List<Route> routes1 = prDao.GetRoutes(start, dest);
            return routes1;
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void setRoutes(Route routes) {
        Controller.routes = routes;
    }

    public static void setForCityPanel(City c) {
        cPanel =c;
    }

    public static void SaveCity(String name, Integer coordX, Integer coordY) {
        FileDao fDao = new FileDao();
        try {
            fDao.SaveCity(new City(coordX, coordY, name));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void DeleteCity(City city) {
        FileDao fDao = new FileDao();
        try {
            fDao.DeleteCity(city);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void AddCityConn(City start, City dest){
        JavaProlog jp = new JavaProlog();
        jp.AddCityConn(start.getName(), dest.getName(), "0000");
    }
    
    public static void DelCityConn(City start, City dest){
        JavaProlog jp = new JavaProlog();
        jp.DelCityConn(start.getName(), dest.getName(), "0000");
        
    }

}
