/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.List;
import java.util.Objects;

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
        String output = "";
        for (int i = 0; i < cityList.size(); i++) {
            output += cityList.get(i).getName() + (i < cityList.size() - 1 ? " > " : " ");
        }
        output += " Dist: " + distance;
        return output;

    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Route other = (Route) obj;
        if (this.cityList.size() == 2 && other.cityList.size() == 2) {
            if (!Objects.equals(this.cityList.get(0).getName(), other.cityList.get(1).getName())
                    || !Objects.equals(this.cityList.get(1).getName(), other.cityList.get(0).getName())) {
                return false;
            }
        } else {
            if (!Objects.equals(this.cityList, other.cityList)) {
                return false;
            }
        }
        return true;
    }

}
