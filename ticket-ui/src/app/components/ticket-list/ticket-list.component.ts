import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Ticket } from 'src/app/model/ticket';
import { TicketService } from 'src/app/service/ticket.service';

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.css']
})
export class TicketListComponent implements OnInit {

  tickets: Ticket[];


  constructor(private ticketService : TicketService, private route : ActivatedRoute) {

  }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  listTicket() {
    this.ticketService.getAllTickets().subscribe(
      (data:any) => {
        this.tickets = data;
      }
    )
  }
}
