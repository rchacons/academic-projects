import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/model/user';
import { PersonService } from 'src/app/service/person.service';
import { PersonListComponent } from '../person-list/person-list.component';


@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html'
})
export class UserListComponent extends PersonListComponent implements OnInit{

  users : User[]

  constructor(personService : PersonService, route : ActivatedRoute){
    super(personService, route);
  }

  override ngOnInit(): void {
    super.ngOnInit();
    this.listUsers();
  }

  listUsers(){
    this.personService.getAllUsers().subscribe((data:any) => {
      this.users = data;
    })
  }


}
