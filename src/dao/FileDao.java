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

    public void DeleteCity(City city) throws IOException {
        String remove = city.getName() + "," + city.getCoordX() + "," + city.getCoordY();

        File inFile = new File(saveURL);

        if (!inFile.isFile()) {
            System.out.println("Parameter is not an existing file");
            return;
        }
        //Construct the new file that will later be renamed to the original filename.
        File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

        BufferedReader br = new BufferedReader(new FileReader(saveURL));
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

        String line = null;

      //Read from the original file and write to the new
        //unless content matches data to be removed.
        while ((line = br.readLine()) != null) {

            if (!line.trim().equals(remove)) {

                pw.println(line);
                pw.flush();
            }
        }
        pw.close();
        br.close();

        //Delete the original file
        if (!inFile.delete()) {
            System.out.println("Could not delete file");
            return;
        }

        //Rename the new file to the filename the original file had.
        if (!tempFile.renameTo(inFile)) {
            System.out.println("Could not rename file");
        }

    }

}
