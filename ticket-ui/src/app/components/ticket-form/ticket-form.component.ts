import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Ticket } from 'src/app/model/ticket';
import { TicketService } from 'src/app/service/ticket.service';

@Component({
  selector: 'app-ticket-form',
  templateUrl: './ticket-form.component.html',
  styleUrls: ['./ticket-form.component.css']
})
export class TicketFormComponent {

  ticket: Ticket;

  constructor( private route : ActivatedRoute, private router: Router, 
    private ticketService : TicketService) {
      this.ticket = new Ticket();
    }

    onSubmit() {
      this.ticketService.addTicket(this.ticket).subscribe(
        result =>  this.gotoTicketList()
      );
    }
  
    gotoTicketList() {
      this.router.navigate(['/tickets']);
    }

}
