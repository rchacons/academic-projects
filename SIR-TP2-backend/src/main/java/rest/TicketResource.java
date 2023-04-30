package rest;

import dto.TicketDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.checkerframework.checker.units.qual.A;
import org.jboss.resteasy.plugins.providers.ReaderProvider;
import service.TicketService;

import javax.naming.InvalidNameException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("/ticket")
public class TicketResource {

    TicketService ticketService = new TicketService();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Find ticket by its id"
    )
    public TicketDTO getTicketById(@PathParam("id") String id) {
        return ticketService.getTicketById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Retrieve all ticket",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tickets were retrieved with success"),
                    @ApiResponse ( responseCode = "400", description = "Any ticket exist")
            }
    )
    public Response getAllTicket() {
        try {
            List <TicketDTO> list = this.ticketService.getAllTicketsDto();
            return Response.ok().
                    entity(list).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Creates ticket",
            responses = {
                    @ApiResponse( responseCode = "200", description = "Ticket created with success"),
                    @ApiResponse( responseCode = "400", description = "User provided doesn't exists")
            })
    public Response createTicket(TicketDTO ticket) {
        try {
            TicketDTO ticketDTO = ticketService.createTicket(ticket);
            return Response.ok().
                    entity(ticketDTO).
                    build();
        }
        catch (InvalidNameException e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }

    }
}
