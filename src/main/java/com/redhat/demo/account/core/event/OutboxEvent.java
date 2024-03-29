package com.redhat.demo.account.core.event;

import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "accounts_outbox")
@Access(AccessType.FIELD)
public class OutboxEvent extends PanacheEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "aggregatetype")
    private String aggregateType;

    @Column(name = "aggregateid")
    private String aggregateId;

    @Column(name = "payload")
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String payload;

    @Column(name = "content_type")
    private String contentType;

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
