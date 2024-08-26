/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Orders;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Admins
 */
public class Order {
    private String orderID;
    private String userID;
    private Date date;
    private BigDecimal total;
    private boolean status;

    public Order() {}

    public Order(String orderID, String userID, Date date, BigDecimal total, boolean status) {
        this.orderID = orderID;
        this.userID = userID;
        this.date = date;
        this.total = total;
        this.status = status;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
