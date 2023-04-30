package dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class SupportMemberDTO extends PersonDTO{

    @Schema(description = "List of tickets assigned to the member", nullable = true)
    private List<TicketDTO> assignedTicketsList;

    public List<TicketDTO> getAssignedTicketsList() {
        return assignedTicketsList;
    }

    public void setAssignedTicketsList(List<TicketDTO> assignedTicketsList) {
        this.assignedTicketsList = assignedTicketsList;
    }


}
