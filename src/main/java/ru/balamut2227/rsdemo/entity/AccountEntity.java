/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.balamut2227.rsdemo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Balamut2227
 */

@Entity
@Table(name="rs_demo.RS_ACCOUNTS")
public class AccountEntity implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name="key_acc")
    private long keyAcc;
    
    @Column(name="acc_id")
    private java.math.BigDecimal accId;

    @Column(name="current_balance")
    private java.math.BigDecimal currentBalance;
    
    public AccountEntity(java.math.BigDecimal accId) {
        this.accId = accId;
    }

    public AccountEntity() {
    }

    public java.math.BigDecimal getAccId() {
        return accId;
    }
    public void setAccId(java.math.BigDecimal accId) {
        this.accId = accId;
    }

    public Long getKeyAcc() {
        return keyAcc;
    }

    public void setKeyAcc(Long keyAcc) {
        this.keyAcc = keyAcc;
    }

    public java.math.BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(java.math.BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }
    
}
