/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.util.List;

/**
 *
 * @author Krisztian
 */
public class Route {
    private List<City> cityList;
    private Integer distance;

    public Route(List<City> cityList, Integer distance) {
        this.cityList = cityList;
        this.distance = distance;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        String output="";
        for(int i=0;i<cityList.size();i++){
            output+=cityList.get(i).getName()+ (i<cityList.size()-1 ? " > ":" ");
        }
        output+=" Dist: "+distance;
        return output;
        
    }
    
    
    
    
    
    
}
