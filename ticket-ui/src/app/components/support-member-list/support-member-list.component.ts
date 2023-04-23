import { Component, OnInit } from '@angular/core';
import { SupportMember } from 'src/app/model/supportMember';
import { PersonService } from 'src/app/service/person.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-support-member-list',
  templateUrl: './support-member-list.component.html',
  styleUrls: ['./support-member-list.component.css']
})
export class SupportMemberListComponent {

  supportMembers : SupportMember[]

  constructor(private personService : PersonService, private route : ActivatedRoute){}

  ngOnInit(): void {
      this.listSupportMembers();
  }

  listSupportMembers(){
    this.personService.getAllSupportMember().subscribe((data:any) => {
      this.supportMembers = data;
    })
  }

}

