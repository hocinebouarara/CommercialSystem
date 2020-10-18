package SaleDashboard.Otiles;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

import java.awt.*;

public class produit {
    private int id;
    private String nom_pro;
    private float prix_pro;
    private int Quantite;
    private double prix;


    public produit(int id, String nom_pro, float prix_pro, int Quantite, float prix) {
        this.id = id;
        this.nom_pro = nom_pro;
        this.prix_pro = prix_pro;
        this.Quantite = Quantite;
        this.prix = prix;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_pro() {
        return nom_pro;
    }

    public void setNom_pro(String nom_pro) {
        this.nom_pro = nom_pro;
    }

    public float getPrix_pro() {
        return prix_pro;
    }

    public void setPrix_pro(float prix_pro) {
        this.prix_pro = prix_pro;
    }

    public int getContity() {
        return Quantite;
    }

    public void setContity(int contity) {
        this.Quantite = contity;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }
}
