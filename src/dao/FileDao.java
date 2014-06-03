/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.City;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Krisztian
 */
public class FileDao {

    private final String cityURL = "/src/city.dat";
    private final String CHAR_SET = "UTF-8";

        public List<City> GetCities() throws Exception{
        List<City> cityList = new ArrayList<>();
        InputStream ins = getClass().getResourceAsStream(cityURL);
        Scanner fileScanner = new Scanner(ins, CHAR_SET);
        Scanner rowScanner;

        String name;
        Integer coordX;
        Integer coordY;
        
        while (fileScanner.hasNextLine()) {
            rowScanner = new Scanner(fileScanner.nextLine());
            rowScanner.useDelimiter(",");
            name = rowScanner.next();
            coordX = rowScanner.nextInt();
            coordY = rowScanner.nextInt();
            
            cityList.add(new City(coordX, coordY, name));
        }

        return cityList;
    }

}
