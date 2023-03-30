import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Person } from '../model/person';


@Injectable({
  providedIn: 'root'
})
export class PersonService {

  protected getPersonUrl = `${environment.ticketApi}/ticket/`;
  
  constructor(private http: HttpClient) { }

  getPersonById(id : string) : Observable<Person> {
    return this.http.get<Person>(this.getPersonUrl+id).pipe(
      tap((data) => console.log('Person :' +JSON.stringify(data)))
    );
  }

}
