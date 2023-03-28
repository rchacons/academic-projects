import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Ticket } from '../model/ticket';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  protected getTicketUrl = `${environment.ticketApi}/ticket/`


  constructor(private http : HttpClient) { }

  getTicketById(id : string) : Observable <Ticket>  {
    return this.http.get<Ticket>(this.getTicketById+id).pipe( 
      tap((data)=> console.log('All:' + JSON.stringify(data)))
    );
  }


}
