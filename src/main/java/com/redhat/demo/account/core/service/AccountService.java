package com.redhat.demo.account.core.service;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import com.redhat.demo.account.core.entity.Account;
import com.redhat.demo.account.core.entity.AccountStatus;
import com.redhat.demo.account.core.event.OutboxEvent;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.core.format.EventFormat;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;

@ApplicationScoped
public class AccountService {

    @Inject
    EntityManager entityManager;

    @ConfigProperty(name = "account-event.aggregate.type", defaultValue = "account-event")
    String aggregateType;

    @Transactional
    public Long create(Account account) {
        account.setStatus(AccountStatus.CREATED);
        entityManager.persist(account);

        OutboxEvent outboxEvent = buildOutboxEvent(account);
        entityManager.persist(outboxEvent);
        entityManager.remove(outboxEvent);

        return account.getId();
    }

    @Transactional
    public Account get(Long id) {
        return entityManager.find(Account.class, id, LockModeType.OPTIMISTIC);
    }

    OutboxEvent buildOutboxEvent(Account account) {
        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.setAggregateType(aggregateType);
        outboxEvent.setAggregateId(Long.toString(account.getId()));
        outboxEvent.setContentType("application/cloudevents+json; charset=UTF-8");
        outboxEvent.setPayload(toCloudEvent(account));

        return outboxEvent;
    }

    String toCloudEvent(Account account) {
        CloudEvent event = CloudEventBuilder.v1().withType("AccountCreatedEvent").withTime(OffsetDateTime.now())
                .withSource(URI.create("demo/account")).withDataContentType("application/json")
                .withId(UUID.randomUUID().toString()).withData(account.toJson().encode().getBytes()).build();
        EventFormat format = EventFormatProvider.getInstance().resolveFormat(JsonFormat.CONTENT_TYPE);

        return new String(format.serialize(event));
    }

}
