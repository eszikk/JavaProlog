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
    private List<String> cityList;
    private Integer distance;

    public Route(List<String> cityList, Integer distance) {
        this.cityList = cityList;
        this.distance = distance;
    }

    public List<String> getCityList() {
        return cityList;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        String output="Cities:\n";
        
        for(String s:cityList){
            output+=s+"\n";
        }
        output+="Distance: "+distance;
        return output;
        
    }
    
    
    
    
    
    
}
