/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Route;
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

    private final String prologURL = "C:/Users/Krisztian/Documents/NetBeansProjects/Routes/src/src/uj.pl";
    private Pattern pattern = Pattern.compile("loc\\((.+?)\\)");

    /**
     * Query the routes and distances from prolog.
     * @param start start city name
     * @param dest  destination city name
     * @return return with Route type
     */
    public List<Route> GetRoutes(String start, String dest) {
        List<Route> routes = new ArrayList<>();
        List<String> tagValues;
        String connStr = "consult(" + prologURL + ")";
        String getRoutesStr = "bfs(loc(" + start + "), loc(" + dest + "), X,D)";

        Query conn = new Query(connStr);
        System.out.println(connStr + " " + (conn.hasSolution() ? "Succes" : "Fail"));

        Query getRoutes = new Query(getRoutesStr);

        while (getRoutes.hasMoreSolutions()) {
            tagValues = new ArrayList<>();

            Term t1 = (Term) getRoutes.nextSolution().get("X");
            String st = t1.toString();
            Term t2 = (Term) getRoutes.nextSolution().get("D");
            Integer dist = Integer.parseInt(t2.toString());

            Matcher matcher = pattern.matcher(st);

            while (matcher.find()) {
                tagValues.add(matcher.group(1));
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
        String getConnCityStr = "setof(Y,bfs(loc(omaha),X,Y),_)";
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
