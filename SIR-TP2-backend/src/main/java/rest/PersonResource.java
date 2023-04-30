package rest;

import dto.PersonDTO;
import dto.SupportMemberDTO;
import dto.TicketDTO;
import dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import service.PersonService;

import javax.naming.InvalidNameException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/person")
public class PersonResource {

    PersonService personService = new PersonService();

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Find user/supportMember by its id"
    )
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO getPersonById(@PathParam("id") String id){
        return personService.getPersonById(id);
    }

    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Creates user",
            responses = {
                    @ApiResponse( responseCode = "200", description = "User saved with success"),
                    @ApiResponse( responseCode = "400", description = "Username already exists")
    }
    )
    public Response createPerson(
            @Parameter(
                    description = "User to be saved"
            ) UserDTO person) {
        try {
            PersonDTO personDTO = personService.createPerson(person);
            return Response.ok().
                    entity(personDTO).
                    build();
        }
        catch (InvalidNameException e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }

    }

    @POST
    @Path("/support")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Creates support member",
            responses = {
                    @ApiResponse( responseCode = "200", description = "Support member saved with success"),
                    @ApiResponse( responseCode = "400", description = "Name already exists")
            }
    )
    public Response createPerson(SupportMemberDTO supportMemberDTO) {
        try {
            PersonDTO personDTO = personService.createPerson(supportMemberDTO);
            return Response.ok().
                    entity(personDTO).
                    build();
        }
        catch (InvalidNameException e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }

    }

    @GET
    @Path("/user")
    @Operation(
            summary = "Gets all users"
    )
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonDTO> getAllUser(){
        return personService.getUsers();
    }


    @GET
    @Path("/support")
    @Operation(
            summary = "Gets all support members"
    )
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonDTO> getAllSupportMember(){
        return personService.getSupportMembers();
    }
}
