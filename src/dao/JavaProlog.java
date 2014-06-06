/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.City;
import entity.Route;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jpl.Query;
import jpl.Term;

/**
 *
 * @author Krisztian
 */
public class JavaProlog {

    private final String prologUtURL = "src/data/ut.pl";
    private final String prologCityConnURL = "src/data/cityConn.dat";
    private Pattern pattern = Pattern.compile("loc\\((.+?)\\)");
    
    /**
     * Consulting with prolog files.
     */
    public void Consult(){
        //String connStrUt_ = "consult('"+ prologUtURL +"')";
        String connStrUt = "mod1:consult('"+prologUtURL+"')";
        Query connUt = new Query(connStrUt);
        System.out.println(connStrUt + " " + (connUt.hasSolution() ? "Succes" : "Fail"));
        //String connStrCityConn = "consult('"+ prologCityConnURL +"')";
        String connStrCityConn = "mod2:consult('"+prologCityConnURL+"')";
        Query connCityConn = new Query(connStrCityConn);
        System.out.println(connStrCityConn + " " + (connCityConn.hasSolution() ? "Succes" : "Fail"));
    }

    /**
     * Query the routes and distances from prolog.
     * @param start start city name
     * @param dest  destination city name
     * @return return with Route type
     */
    public List<Route> GetRoutes(String start, String dest) throws IOException{
        Consult();
        List<Route> routes = new ArrayList<>();
        List<City> tagValues;
        
        String getRoutesStr = "bfs(loc(" + start + "), loc(" + dest + "), X,D)";
        Query getRoutes = new Query(getRoutesStr);
        
        FileDao fDao = new FileDao();
        List<City> cities = fDao.GetCities();
        Integer cityX=0;
        Integer cityY=0;
        
        while (getRoutes.hasMoreSolutions()) {
            tagValues = new ArrayList<>();

            Term t1 = (Term) getRoutes.nextSolution().get("X");
            String st = t1.toString();
            Term t2 = (Term) getRoutes.nextSolution().get("D");
            Integer dist = Integer.parseInt(t2.toString());

            Matcher matcher = pattern.matcher(st);
            
            
            
            while (matcher.find()) {
                for(City c: cities){
                    if(matcher.group(1).equals(c.getName())){
                        cityX = c.getCoordX();
                        cityY = c.getCoordY();
                    }
                }
                
                tagValues.add(new City(cityX,cityY,matcher.group(1)));
            }
            routes.add(new Route(tagValues, dist));
        }

        return routes;
    }

    /**
     * Query the connected cities name.
     * @param name cityname
     * @return return with String
     */
    public List<String> GetConnectedCities(String name) {
        Consult();
        String getConnCityStr = "setof(Y,bfs(loc("+name+"),X,Y),_)";
        Query q5 = new Query(getConnCityStr);

        List<String> tagValues = new ArrayList<>();
        while (q5.hasMoreSolutions()) {

            Term t1 = (Term) q5.nextSolution().get("X");
            String st = t1.toString();

            Matcher matcher = pattern.matcher(st);

            while (matcher.find()) {
                tagValues.add(matcher.group(1));
            }
            tagValues.remove(name);
        }

        return tagValues;

    }

}
