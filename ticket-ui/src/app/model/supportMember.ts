import { Ticket } from "./ticket"

export interface SupportMember {
    id: number
    name: string
    type: string
    assignedTicketsList: Ticket[]
  }