/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Date;

/**
 *
 * @author hocin
 */
public class PurchaseDelivery {

    int col1, col2, col3;
    String col4;
    float col5;
    int col6;
    float col7;
    int col8;
    String col9;
    Date col10;

    public PurchaseDelivery(int col1, int col2, int col3, String col4, float col5, int col6, float col7, int col8, String col9, Date col10) {
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
        this.col5 = col5;
        this.col6 = col6;
        this.col7 = col7;
        this.col8 = col8;
        this.col9 = col9;
        this.col10 = col10;
    }

    public int getCol1() {
        return col1;
    }

    public void setCol1(int col1) {
        this.col1 = col1;
    }

    public int getCol2() {
        return col2;
    }

    public void setCol2(int col2) {
        this.col2 = col2;
    }

    public int getCol3() {
        return col3;
    }

    public void setCol3(int col3) {
        this.col3 = col3;
    }

    public String getCol4() {
        return col4;
    }

    public void setCol4(String col4) {
        this.col4 = col4;
    }

    public float getCol5() {
        return col5;
    }

    public void setCol5(float col5) {
        this.col5 = col5;
    }

    public int getCol6() {
        return col6;
    }

    public void setCol6(int col6) {
        this.col6 = col6;
    }

    public float getCol7() {
        return col7;
    }

    public void setCol7(float col7) {
        this.col7 = col7;
    }

    public int getCol8() {
        return col8;
    }

    public void setCol8(int col8) {
        this.col8 = col8;
    }

    public String getCol9() {
        return col9;
    }

    public void setCol9(String col9) {
        this.col9 = col9;
    }

    public Date getCol10() {
        return col10;
    }

    public void setCol10(Date col10) {
        this.col10 = col10;
    }

}
