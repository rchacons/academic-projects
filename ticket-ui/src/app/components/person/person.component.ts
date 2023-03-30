import { Component } from '@angular/core';
import { Person } from 'src/app/model/person';
import { PersonService } from 'src/app/service/person.service';

@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.css']
})
export class PersonComponent {

  person? : Person;
  
  constructor(personService : PersonService){
    // test
    personService.getPersonById("10").subscribe(person => {
      if (person) {
        console.log('Got person:', person);
        this.person = person;
      } else {
        console.error('No person found');}
    });
  }

}
