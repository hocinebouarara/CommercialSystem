/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Date;

/**
 *
 * @author Deeplight
 */
public class Property {
    
    int id , id_propr;
    String nom ,article ,titre ,commune,reu,acie;
    Date date;

    public Property(int id, int id_propr, String nom, String article, String titre, String commune, String reu, String acie, Date date) {
        this.id = id;
        this.id_propr = id_propr;
        this.nom = nom;
        this.article = article;
        this.titre = titre;
        this.commune = commune;
        this.reu = reu;
        this.acie = acie;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_propr() {
        return id_propr;
    }

    public void setId_propr(int id_propr) {
        this.id_propr = id_propr;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getReu() {
        return reu;
    }

    public void setReu(String reu) {
        this.reu = reu;
    }

    public String getAcie() {
        return acie;
    }

    public void setAcie(String acie) {
        this.acie = acie;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    
}
