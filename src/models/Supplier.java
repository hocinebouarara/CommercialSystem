/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author hocin
 */
public class Supplier {

    int supplierId;

    String name, agent, adress, city, phone, fax, cnss;

    public Supplier(int supplierId, String name, String agent, String adress, String city, String phone, String fax, String cnss) {
        this.supplierId = supplierId;
        this.name = name;
        this.agent = agent;
        this.adress = adress;
        this.city = city;
        this.phone = phone;
        this.fax = fax;
        this.cnss = cnss;
    }

    public Supplier(int supplierId, String name, String agent, String adress, String phone) {
        this.supplierId = supplierId;
        this.name = name;
        this.agent = agent;
        this.adress = adress;
        this.phone = phone;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCnss() {
        return cnss;
    }

    public void setCnss(String cnss) {
        this.cnss = cnss;
    }

}
