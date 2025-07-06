import { Component, OnInit } from '@angular/core';
import { SupportMember } from 'src/app/model/supportMember';
import { PersonService } from 'src/app/service/person.service';
import { ActivatedRoute } from '@angular/router';
import { PersonListComponent } from '../person-list/person-list.component';


@Component({
  selector: 'app-support-member-list',
  templateUrl: './support-member-list.component.html',
})
export class SupportMemberListComponent extends PersonListComponent implements OnInit{

  supportMembers : SupportMember[]

  constructor(personService : PersonService, route : ActivatedRoute){
    super(personService, route);
  }

  override ngOnInit(): void {
    super.ngOnInit();
    this.listSupportMembers();
  }

  listSupportMembers(){
    this.personService.getAllSupportMember().subscribe((data:any) => {
      this.supportMembers = data;
    })
  }

}

