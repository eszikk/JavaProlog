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
     * @throws java.io.IOException
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
        Query q = new Query(getConnCityStr);

        List<String> tagValues = new ArrayList<>();
        while (q.hasMoreSolutions()) {

            Term t1 = (Term) q.nextSolution().get("X");
            String st = t1.toString();

            Matcher matcher = pattern.matcher(st);

            while (matcher.find()) {
                tagValues.add(matcher.group(1));
            }
            tagValues.remove(name);
        }
        return tagValues;
    }

    public List<City> GetCitiesWhichHaveConn() throws IOException {
        Consult();
        FileDao fdao = new FileDao();
        List<City> temp = new ArrayList<>();
        List<City> result = new ArrayList<>();
        List<City> flist = fdao.GetCities();
        String getCities = "move(loc(X),loc(_),_).";
        Query q = new Query(getCities);

        while (q.hasMoreSolutions()) {
            Term t1 = (Term) q.nextSolution().get("X");
            String st = t1.toString();
            temp.add(new City(0, 0, st));
        }
        //remove duplicates
        for (City c : temp) {
            if (!result.contains(c)) {
                result.add(c);
            }
        }

        return MergeDAOs(result);

    }
    
    private List<City> MergeDAOs(List<City> list) throws IOException{
        FileDao fdao = new FileDao();
        List<City> result = list;
        List<City> flist = fdao.GetCities();
        
                for (City rc : result) {
            for (City fc : flist) {
                if (rc.equals(fc)) {
                    rc.setCoordX(fc.getCoordX());
                    rc.setCoordY(fc.getCoordY());
                }

            }
        }
        
        
        return result;
        
    }

    public Boolean AddCityConn(String start, String dest, String dist) {
        Consult();

        String addCityConn = "assert(move(loc(" + start + "), loc(" + dest + ")," + dist + "))";
        Query addRoutes = new Query(addCityConn);
        Boolean r1 = addRoutes.hasSolution();

        String addCityConn2 = "assert(move(loc(" + dest + "), loc(" + start + ")," + dist + "))";
        Query addRoutes2 = new Query(addCityConn2);
        Boolean r2 = addRoutes2.hasSolution();
        System.out.println("PROLOG: " +start+" "+dest+" "+dist);
        SaveData();
        Consult();
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

    public Boolean IsP2PConnectedCities(String start, String dest) {
        Consult();

        String connCityStr = "move(loc(" + start + "), loc(" + dest + "), _).";
        Query connCityConn = new Query(connCityStr);
        Boolean r1 = connCityConn.hasSolution();

        String connCityStr2 = "move(loc(" + dest + "), loc(" + start + "), _).";
        Query connCityConn2 = new Query(connCityStr2);
        Boolean r2 = connCityConn.hasSolution();

        if (r1 && r2) {
            return true;
        } else {
            return false;
        }

    }
    
    public List<Route> GetP2PCities() throws IOException{
        Consult();
    
        List<Route> result = new ArrayList<>();
        List<Route> tmp = new ArrayList<>();
        String getCitiesStr = "move(loc(X),loc(Y),D)";
        Query getCities = new Query(getCitiesStr);
        
        while (getCities.hasMoreSolutions()) {

            Term t1 = (Term) getCities.nextSolution().get("X");
            String start = t1.toString();
            Term t2 = (Term) getCities.nextSolution().get("Y");
            String dest = t2.toString();
            Term t3 = (Term) getCities.nextSolution().get("D");
            String dist = t3.toString();
            List<City> temp = new ArrayList<>();
            temp.add(new City(0, 0, start));
            temp.add(new City(0, 0, dest));
            tmp.add(new Route(MergeDAOs(temp), Integer.parseInt(dist)));
            
        }
                for (Route c : tmp) {
            if (!result.contains(c)) {
                result.add(c);
            }
        }

        return result;
    }
}


