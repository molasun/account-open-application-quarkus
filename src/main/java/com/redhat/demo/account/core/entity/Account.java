package com.redhat.demo.account.core.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.vertx.core.json.JsonObject;

@Entity
@Table(name = "accounts")
@SequenceGenerator(name = "AccountsSeq", sequenceName = "account_sequence", allocationSize = 10)
public class Account extends PanacheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "AccountsSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "customer_last_name")
    private String customerLastName;

    @Column(name = "customer_first_name")
    private String customerFirstName;

    @Column(name = "account_balance")
    private BigDecimal accountBalance;

    @Column(name = "currency")
    private String currnecy;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private AccountStatus status;

    @Column(name = "version")
    @Version
    @JsonIgnore
    private long version;

    public Long getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getCurrnecy() {
        return currnecy;
    }

    public void setCurrnecy(String currnecy) {
        this.currnecy = currnecy;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public long getVersion() {
        return version;
    }

    public JsonObject toJson() {
        return new JsonObject().put("id", id).put("accountId", accountId).put("accountType", accountType)
                .put("customerId", customerId).put("customerLastName", customerLastName)
                .put("customerFirstName", customerFirstName).put("accountBalance", accountBalance)
                .put("currnecy", currnecy).put("status", status != null ? status.toString() : null);
    }
}
