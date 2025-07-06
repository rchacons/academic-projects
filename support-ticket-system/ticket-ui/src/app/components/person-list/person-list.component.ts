import { Component, ContentChild, Input, OnInit, TemplateRef} from '@angular/core';
import { Person } from 'src/app/model/person';
import { PersonService } from 'src/app/service/person.service';
import { ActivatedRoute } from '@angular/router';



@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html'
})
export class PersonListComponent implements OnInit {

  personList : Person[]

  @ContentChild('headerTemplate', { static: true }) headerTemplate: TemplateRef<any>;
  @ContentChild('bodyTemplate', { static: true }) bodyTemplate: TemplateRef<any>;

  constructor(protected personService : PersonService, protected route : ActivatedRoute){}

  ngOnInit(): void {}

}
