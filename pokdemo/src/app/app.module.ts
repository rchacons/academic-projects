import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MyComponent } from './my-component/my-component.component';

import { FormsModule } from '@angular/forms';
import { FilterPokemonPipe } from './fileter-pokemon.pipe';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatFormFieldModule } from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select'; 
import {MatInputModule} from '@angular/material/input'; 
import { MatIconModule } from '@angular/material/icon'


import { HttpClientModule } from '@angular/common/http';
import { PokeService } from './poke-service.service';
import { PokemonDetailsComponent } from './pokemon-details-component/pokemon-details-component.component';

@NgModule({
  declarations: [
    AppComponent,
    MyComponent,
    FilterPokemonPipe,
    PokemonDetailsComponent,
  ],
  imports: [
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,    
    MatIconModule,
    HttpClientModule
  ],
  providers: [PokeService] ,
  bootstrap: [AppComponent]
})
export class AppModule { }
