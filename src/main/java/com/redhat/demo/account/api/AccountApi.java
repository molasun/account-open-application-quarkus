package com.redhat.demo.account.api;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.redhat.demo.account.core.entity.Account;
import com.redhat.demo.account.core.service.AccountService;

@Path("/account")
public class AccountApi {

    @Inject
    AccountService accountService;

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(Account account) {

        Long id = accountService.create(account);
        return Response.created(URI.create("account/" + id)).build();

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@PathParam("id") Long accountId) {

        Account account = accountService.get(accountId);
        return account != null ? Response.ok(account).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

}
