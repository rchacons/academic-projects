import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Ticket } from 'src/app/model/ticket';
import { PersonService } from 'src/app/service/person.service';
import { TicketService } from 'src/app/service/ticket.service';

@Component({
  selector: 'app-ticket-form',
  templateUrl: './ticket-form.component.html',
  styleUrls: ['./ticket-form.component.css']
})
export class TicketFormComponent  implements OnInit {

  ticket: Ticket;
  users: any;

  constructor( private route : ActivatedRoute, private router: Router, 
    private ticketService : TicketService, private personService: PersonService) {
      this.ticket = new Ticket();
    }
  ngOnInit(): void {
    this.getAllUser();
  }

  onSubmit() {
      this.ticketService.addTicket(this.ticket).subscribe(
        () =>  this.gotoTicketList()
      );
  }

  gotoTicketList() {
      this.router.navigate(['/tickets']);
  }

  getAllUser() {
    this.personService.getAllUsers().subscribe(
      (data:any) => {
        this.users = data;
      }
    )
  }
}
