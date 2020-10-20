/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SaleDashboard.Otiles;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author hamid
 */
public class Convert_date {
   public static Date Convert(LocalDate date){
       
       ZoneId id=ZoneId.systemDefault();
       return java.sql.Date.from(date.atStartOfDay(id).toInstant());
       
   }
   public static java.sql.Date CovertToSqlDate(Date date){
       java.sql.Date d=new java.sql.Date(date.getTime());
       return d;
   }
}
