/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import dao.FileDao;
import entity.City;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Krisztian
 */
public class Controller {
    private static String mapImageURL ="C:\\Users\\Krisztian\\Documents\\NetBeansProjects\\Routes\\src\\src\\map.gif";
    private static Image map;
    private static Integer mapWidth=600;
    private static Integer mapHeight=445;
    private static Integer mapX=0;
    private static Integer mapY=0;
    
    
/**
 * Draws the map to the panel.
 * @param g 
 */
    public static void DrawMap(Graphics g) {
        try {
            map = ImageIO.read(new File(mapImageURL));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.drawImage(map, mapX, mapY, mapWidth, mapHeight, null);
    
    }
    /**
     * Get city list from file dao and pass torward.
     * @return 
     */
    public static List<City> GetCities(){
        FileDao dao = new FileDao();
        try {
            return dao.GetCities();
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    
    
}
