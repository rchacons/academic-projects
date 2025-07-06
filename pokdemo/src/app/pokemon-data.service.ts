import { ThisReceiver } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PokemonDataService {

  // Observable that can be subscribed to in order to received updates
  private _selectedPokemonName = new Subject<string>();

  getSelectedPokemonId() : Observable<string> {
    return this._selectedPokemonName.asObservable();
  }

  setSelectedPokemon(name : string){
    // The next() allows to emit a new value to its subscribers
    this._selectedPokemonName.next(name);
  }
}