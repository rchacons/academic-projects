import { Person } from "./person"
import { Ticket } from "./ticket"

export interface SupportMember extends Person{
  
    assignedTicketsList: Ticket[]
  }