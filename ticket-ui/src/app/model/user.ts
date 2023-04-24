import { Person } from "./person"
import { Ticket } from "./ticket"

export interface User extends Person{
    createdTicketsList: Ticket[]
  }