import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Person } from '../model/person';
import { SupportMember } from '../model/supportMember';
import { User } from '../model/user';


@Injectable({
  providedIn: 'root'
})
export class PersonService {

  protected personsUrl = `${environment.ticketApi}/person/`;
  
  constructor(private http: HttpClient) { }

  getPersonById(id : string) : Observable<Person> {
    return this.http.get<Person>(this.personsUrl+id).pipe(
      tap((data) => console.log('Person :' +JSON.stringify(data)))
    );
  }

  saveUser(user : User){
    return this.http.post<User>(this.personsUrl,user)
  }

  saveSupportMember(supportMember : SupportMember){
    return this.http.post<User>(this.personsUrl,supportMember)
  }

}
