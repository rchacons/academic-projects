import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/model/user';
import { PersonService } from 'src/app/service/person.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit{

  users : User[]

  constructor(private personService : PersonService, private route : ActivatedRoute){}

  ngOnInit(): void {
      this.listUsers();
  }

  listUsers(){
    this.personService.getAllUsers().subscribe((data:any) => {
      this.users = data;
    })
  }


}
