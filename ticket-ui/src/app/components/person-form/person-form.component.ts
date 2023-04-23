import { Component } from '@angular/core';
import { Person } from 'src/app/model/person';
import { ActivatedRoute, Router } from '@angular/router';
import { PersonService } from 'src/app/service/person.service';


@Component({
  selector: 'app-person-form',
  templateUrl: './person-form.component.html',
  styleUrls: ['./person-form.component.css']
})
export class PersonFormComponent {

  person: Person;

  constructor( private route : ActivatedRoute, private router: Router, 
    private personService : PersonService) {
      //this.person = new Person();
    }

    // onSubmit() {
    //   if(this.person.type == "User"){
    //     this.personService.saveUser(this.person).subscribe(
    //       result =>  this.gotoPerson()
    //     );
    //   }
    // }
  
    // gotoPerson() {
    //   this.router.navigate(['/person']);
    // }

}
