/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.City;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Krisztian
 */
public class FileDao {

    private final String cityURL = "/data/city.dat";
    private final String saveURL = "src/data/city.dat";
    private final String CHAR_SET = "UTF-8";

    public List<City> GetCities() throws IOException {
        List<City> cityList = new ArrayList<>();
        InputStream ins = getClass().getResourceAsStream(cityURL);
        Scanner fileScanner = new Scanner(ins, CHAR_SET);
        Scanner rowScanner;

        String name;
        Integer coordX;
        Integer coordY;

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            rowScanner = new Scanner(line);
            rowScanner.useDelimiter(",");
            if (!line.isEmpty()) {
                name = rowScanner.next();
                coordX = rowScanner.nextInt();
                coordY = rowScanner.nextInt();

                cityList.add(new City(coordX, coordY, name));
            }
        }
        ins.close();
        return cityList;
    }

    public void SaveCity(City city) throws IOException {
        String name = city.getName().toLowerCase();
        name = name.replaceAll("\\s", "_");
        name = name.replaceAll("\\d", "");
        String data = name + "," + city.getCoordX() + "," + city.getCoordY();

        File file = new File(saveURL);
        System.out.println("" + file.getPath());
        if (!file.exists()) {
            file.createNewFile();
        }

        System.out.println("" + file.getPath());

        FileWriter fw = new FileWriter(file.getPath(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.newLine();
        bw.write(data);
        bw.close();

        System.out.println("Done:" + data);

    }

    public Boolean DeleteCity(City city) throws Exception {
        String lineToRemove = city.getName() + "," + city.getCoordX() + "," + city.getCoordY();
        JavaProlog jPr = new JavaProlog();
        Boolean deleteOk=false;
        Boolean renameOk=false;
        
        File inputFile = new File(saveURL);
        File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if (!trimmedLine.contains(lineToRemove)) {
                writer.write(trimmedLine);
                writer.newLine();
            }
        }
        writer.close();
        reader.close();

        try {
            deleteOk=inputFile.delete();
        } catch (Exception e) {
            throw new IOException("Could not delete the file! " + e);
        }

        try {
            renameOk=tempFile.renameTo(inputFile);
        } catch (Exception e) {
            throw new IOException(" Could not rename the file! " + e);
        }
        
        if(deleteOk && renameOk){
            return jPr.DeleteCityAllConn(city.getName());
        }else{
            return false;
        }
    }

}
