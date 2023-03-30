import { Ticket } from "./ticket"

export interface User {
    id: number
    name: string
    type: string
    createdTicketsList: Ticket[]
  }