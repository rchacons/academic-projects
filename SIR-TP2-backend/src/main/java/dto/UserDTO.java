package dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class UserDTO extends PersonDTO {

    @Schema(description = "List of tickets that the user have created", nullable = true)
    private List<TicketDTO> createdTicketsList;

    public List<TicketDTO> getCreatedTicketsList() {
        return createdTicketsList;
    }

    public void setCreatedTicketsList(List<TicketDTO> createdTicketsList) {
        this.createdTicketsList = createdTicketsList;
    }

}
