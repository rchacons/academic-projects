import { Person } from "./person"
import { Ticket } from "./ticket"

export class User extends Person{
    createdTicketsList: Ticket[]
  }