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
import javax.swing.text.StyledEditorKit;
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
    private Boolean Consult() {

        String connStrUt = "mod1:consult('" + prologUtURL + "')";
        Query connUt = new Query(connStrUt);
        Boolean r1 = connUt.hasSolution();
        
        String connStrCityConn = "mod2:consult('" + prologCityConnURL + "')";
        Query connCityConn = new Query(connStrCityConn);
        Boolean r2 = connCityConn.hasSolution();
        
        return r1 && r2;
    }

    /**
     * Query the routes and distances from prolog.
     *
     * @param start start city name
     * @param dest destination city name
     * @return return with Route type
     */
    public List<Route> GetRoutes(String start, String dest) throws IOException {
        Consult();
        List<Route> routes = new ArrayList<>();
        List<City> tagValues;

        String getRoutesStr = "bfs(loc(" + start + "), loc(" + dest + "), X,D)";
        Query getRoutes = new Query(getRoutesStr);

        FileDao fDao = new FileDao();
        List<City> cities = fDao.GetCities();
        Integer cityX = 0;
        Integer cityY = 0;

        while (getRoutes.hasMoreSolutions()) {
            tagValues = new ArrayList<>();

            Term t1 = (Term) getRoutes.nextSolution().get("X");
            String st = t1.toString();
            Term t2 = (Term) getRoutes.nextSolution().get("D");
            Integer dist = Integer.parseInt(t2.toString());

            Matcher matcher = pattern.matcher(st);

            while (matcher.find()) {
                for (City c : cities) {
                    if (matcher.group(1).equals(c.getName())) {
                        cityX = c.getCoordX();
                        cityY = c.getCoordY();
                    }
                }

                tagValues.add(new City(cityX, cityY, matcher.group(1)));
            }
            routes.add(new Route(tagValues, dist));
        }

        return routes;
    }

    /**
     * Query the connected cities name.
     *
     * @param name cityname
     * @return return with String
     */
    public List<String> GetConnectedCities(String name) {
        Consult();
        String getConnCityStr = "setof(Y,bfs(loc(" + name + "),X,Y),_)";
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

    public Boolean AddCityConn(String start, String dest, String dist) {
        Consult();

        String addCityConn = "assert(move(loc(" + start + "), loc(" + dest + ")," + dist + "))";
        Query addRoutes = new Query(addCityConn);
        Boolean r1 =addRoutes.hasSolution();

        String addCityConn2 = "assert(move(loc(" + dest + "), loc(" + start + ")," + dist + "))";
        Query addRoutes2 = new Query(addCityConn2);
        Boolean r2 =addRoutes2.hasSolution();


        SaveData();
        
        return r1 && r2;
    }

    public Boolean DelCityConn(String start, String dest, String dist) {
        Consult();

        String delCityConn = "retract(move(loc(" + start + "), loc(" + dest + ")," + dist + "))";
        Query delRoutes = new Query(delCityConn);
        Boolean r1 = delRoutes.hasSolution();
        String delCityConn2 = "retract(move(loc(" + dest + "), loc(" + start + ")," + dist + "))";
        Query delRoutes2 = new Query(delCityConn2);
        Boolean r2 = delRoutes2.hasSolution();

        SaveData();
        return r1 && r2;
        
    }

    private Boolean SaveData() {

        String save = "mod2:save('" + prologCityConnURL + "')";
        Query connUt = new Query(save);
        return connUt.hasSolution();

    }

}
