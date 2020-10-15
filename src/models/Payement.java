/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Date;

/**
 *
 * @author hocinebouarara
 */
public class Payement {

    int billId, payementId, deliveryId, productId;
    String desig;
    float price;
    int quantity;
    float total;
    String supplierName;
    Date date;

    public Payement(int billId, int payementId, int deliveryId, int productId, String desig, float price, int quantity, float total, String supplierName, Date date) {
        this.billId = billId;
        this.payementId = payementId;
        this.deliveryId = deliveryId;
        this.productId = productId;
        this.desig = desig;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.supplierName = supplierName;
        this.date = date;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getPayementId() {
        return payementId;
    }

    public void setPayementId(int payementId) {
        this.payementId = payementId;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getDesig() {
        return desig;
    }

    public void setDesig(String desig) {
        this.desig = desig;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    

}
