import { Component } from '@angular/core';
import { Person, TypePerson } from 'src/app/model/person';
import { ActivatedRoute, Router } from '@angular/router';
import { PersonService } from 'src/app/service/person.service';
import { User } from 'src/app/model/user';
import { SupportMember } from 'src/app/model/supportMember';


@Component({
  selector: 'app-person-form',
  templateUrl: './person-form.component.html',
  styleUrls: ['./person-form.component.css']
})
export class PersonFormComponent {

  person: Person;
  personTypeValues = Object.values(TypePerson);

  constructor( private route : ActivatedRoute, private router: Router, 
    private personService : PersonService) {
      this.person = new Person();
    }

     onSubmit() {
      if(this.person.type === TypePerson.USER){
        this.personService.saveUser(this.person as User).subscribe(
          result =>  this.gotoPerson()
         );
       }
      else{
        this.personService.saveSupportMember(this.person as SupportMember).subscribe(
          result =>  this.gotoPerson()
         );
       }
     }
  
    gotoPerson() {
      this.router.navigate(['/person']);
    }

}
