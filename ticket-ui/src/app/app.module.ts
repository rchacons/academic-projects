import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PersonComponent } from './components/person/person.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { TicketListComponent } from './components/ticket-list/ticket-list.component';
import { HomeComponent } from './components/home/home.component';
import { TicketFormComponent } from './components/ticket-form/ticket-form.component';
import { TicketComponent } from './components/ticket/ticket.component';
import { FormsModule } from '@angular/forms';
import { PersonFormComponent } from './components/person-form/person-form.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { SupportMemberListComponent } from './components/support-member-list/support-member-list.component';
import { PersonListComponent } from './components/person-list/person-list.component';

const routes: Routes = [
  {path: 'users', component: UserListComponent},
  {path: 'supportMembers',component: SupportMemberListComponent},
  {path:'tickets',component: TicketListComponent},
  {path:'ticket',component: TicketComponent},
  {path:'addTicket', component: TicketFormComponent},
  {path: 'homePage', component: HomeComponent},
  {path: 'person', component: PersonComponent},
  {path:'addPerson', component: PersonFormComponent},
  {path:'', redirectTo: 'homePage', pathMatch: 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    PersonComponent,
    TicketListComponent,
    HomeComponent,
    TicketFormComponent,
    TicketComponent,
    PersonFormComponent,
    UserListComponent,
    SupportMemberListComponent,
    PersonListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes, {})
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
