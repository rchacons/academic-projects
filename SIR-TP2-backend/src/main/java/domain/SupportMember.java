package domain;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("supportMember")
public class SupportMember extends Person{

    private List<Ticket> ticketList;

    public SupportMember(){
        // Constructor
    }

    @ManyToMany(targetEntity = Ticket.class, mappedBy = "supportMemberList")
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
}
