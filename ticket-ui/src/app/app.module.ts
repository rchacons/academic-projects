import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserComponent } from './components/user/user.component';
import { PersonComponent } from './components/person/person.component';
import { HttpClientModule } from '@angular/common/http';
import { SupportMemberComponent } from './components/support-member/support-member.component';
import { RouterModule, Routes } from '@angular/router';
import { TicketListComponent } from './components/ticket-list/ticket-list.component';
import { HomeComponent } from './components/home/home.component';
import { TicketFormComponent } from './components/ticket-form/ticket-form.component';

const routes: Routes = [
  {path: 'user', component: UserComponent},
  {path:'tickets',component: TicketListComponent},
  {path:'addTicket', component: TicketFormComponent},
  {path: 'homePage', component: HomeComponent},
  {path:'', redirectTo: 'homePage', pathMatch: 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    PersonComponent,
    SupportMemberComponent,
    TicketListComponent,
    HomeComponent,
    TicketFormComponent,
  
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    HttpClientModule,
    RouterModule.forRoot(routes, {})
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
