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
@Table(name="rs_demo.RS_TRANSFERS")
public class TransferEntity implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name="key_transfer")
    private Long keyTransfer;
    
    @Column(name="key_acc_src")
    private Long keyAccSrc;
    
    @Column(name="key_acc_dst")
    private Long keyAccDst;
    
    @Column(name="sum")
    private java.math.BigDecimal sum;
    
    public TransferEntity() {
    }

    public Long getKeyAccSrc() {
        return keyAccSrc;
    }

    public void setKeyAccSrc(Long keyAccSrc) {
        this.keyAccSrc = keyAccSrc;
    }

    public Long getKeyAccDst() {
        return keyAccDst;
    }

    public void setKeyAccDst(Long keyAccDst) {
        this.keyAccDst = keyAccDst;
    }

    public Long getKeyTransfer() {
        return keyTransfer;
    }

    public void getKeyTransfer(Long keyTransfer) {
        this.keyTransfer = keyTransfer;
    }


    public java.math.BigDecimal getSum() {
        return sum;
    }

    public void setSum(java.math.BigDecimal sum) {
        this.sum = sum;
    }

    
   
    
}
