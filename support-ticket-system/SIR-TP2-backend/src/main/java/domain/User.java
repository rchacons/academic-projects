package domain;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("user")
public class User extends Person{

    private List<Ticket> ticketList;

    public User(){
        //Constructor
    }

    @OneToMany(targetEntity = Ticket.class,mappedBy = "user", fetch = FetchType.EAGER)
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

}
