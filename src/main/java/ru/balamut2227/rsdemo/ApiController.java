/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.balamut2227.rsdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.balamut2227.rsdemo.entity.AccountEntity;
import ru.balamut2227.rsdemo.entity.TransferEntity;

/**
 *
 * @author Balamut2227
 */

@RestController
@RequestMapping("/api")
public class ApiController {
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ru.balamut2227_RSDemo_war_1.0PU");
    
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    @RequestMapping(value="/", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String doGetApiRef() {
        
        return "{\"API\":\"OK\"}";
    }
    
    @RequestMapping(value="/accounts", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String doGetAccounts() {
           
        TypedQuery<AccountEntity> qEntities = getEntityManager().createQuery("SELECT u FROM AccountEntity u", AccountEntity.class);
        List<AccountEntity> rl = qEntities.getResultList();
        
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        
        ArrayNode accArrayNode = mapper.createArrayNode();
        Iterator<AccountEntity> it = rl.iterator();
        while (it.hasNext()) {
            AccountEntity accData = it.next();
            //ObjectNode childNode = mapper.createObjectNode();
            ObjectNode childNode = accArrayNode.addObject();
            childNode.put("number", accData.getAccId().toString());
            childNode.put("balance", accData.getCurrentBalance().toString());       
        };
        rootNode.set("accounts", accArrayNode);
        
        String jsonString = "";
        try {
            //return "{\"doGetAccounts\":\"OK\"}";
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ApiController.class.getName()).log(Level.SEVERE, null, ex);
            jsonString = "{\"err\":\"JsonProcessingException\"}";
        }
        
        return jsonString;
    }
    
    @RequestMapping(value="/accounts", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public String doPostAccounts(@RequestParam(name="number", required = true) BigDecimal accId, @RequestParam(name="balance", defaultValue = "0") BigDecimal balance) {
        
        String RES_STATUS_OK = "{\"status\":\"OK\"}";
        String RES_STATUS_ER = "{\"status\":\"ERROR\"}";
        
        EntityManager em = getEntityManager();
        
        TypedQuery<AccountEntity> qEntityByID = em.createQuery("SELECT u FROM AccountEntity u WHERE u.accId=:AID", AccountEntity.class);
        qEntityByID.setParameter("AID", accId);
        List rl = qEntityByID.getResultList();
        
        // если счет уже есть - генерируем ошибку
        if (!rl.isEmpty()) {
            return RES_STATUS_ER;
        }
        
        Query qInsAcc = em.createNativeQuery("INSERT INTO rs_demo.RS_ACCOUNTS (ACC_ID, CURRENT_BALANCE) VALUES (?,?)"); 
        
        qInsAcc.setParameter(1, accId);
        qInsAcc.setParameter(2, balance);
        
        EntityTransaction trn = em.getTransaction();
        trn.begin();
        try {
            
            qInsAcc.executeUpdate();
            
        } catch (Error e) {
            trn.rollback();
            return RES_STATUS_ER;  
        }
        
        trn.commit();
        return RES_STATUS_OK;
        
    }
    
    @RequestMapping(value="/transfers", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String doGetTransfers() {
           
        TypedQuery<TransferEntity> qTransfers = getEntityManager().createQuery("SELECT u FROM TransferEntity u", TransferEntity.class);
        List<TransferEntity> rl = qTransfers.getResultList();
        
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        
        ArrayNode accArrayNode = mapper.createArrayNode();
        Iterator<TransferEntity> it = rl.iterator();
        while (it.hasNext()) {
            TransferEntity accData = it.next();
            
            TypedQuery<AccountEntity> qEntityByKey = getEntityManager().createQuery("SELECT u FROM AccountEntity u WHERE KEY_ACC=:KEY", AccountEntity.class);
            
            qEntityByKey.setParameter("KEY", accData.getKeyAccSrc());
            BigDecimal srcAccId = qEntityByKey.getSingleResult().getAccId();
            
            qEntityByKey.setParameter("KEY", accData.getKeyAccDst());
            BigDecimal dstAccId = qEntityByKey.getSingleResult().getAccId();
            
            ObjectNode childNode = accArrayNode.addObject();
            childNode.put("source", srcAccId.toString());
            childNode.put("dest", dstAccId.toString()); 
            childNode.put("sum", accData.getSum().toString()); 
        };
        rootNode.set("transfers", accArrayNode);
        
        String jsonString = "";
        try {
            //return "{\"doGetAccounts\":\"OK\"}";
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ApiController.class.getName()).log(Level.SEVERE, null, ex);
            jsonString = "{\"err\":\"JsonProcessingException\"}";
        }
        
        return jsonString;
    }
    
    @RequestMapping(value="/transfers", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public String doPostTransfers(@RequestParam(name="source", required = true) BigDecimal accSrc, @RequestParam(name="dest", required = true) BigDecimal accDst, @RequestParam(name="sum", defaultValue = "0") BigDecimal balance) {
        return "{TBD}";
    }
}
