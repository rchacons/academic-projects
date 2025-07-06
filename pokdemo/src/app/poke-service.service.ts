import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class PokeService {

  constructor(private http: HttpClient) { }


  getPokemonList() {
    return this.http.get("https://pokeapi.co/api/v2/pokemon?limit=250");
  }

  getPokemonDetails(pokemonName: string) {
    return this.http.get(`https://pokeapi.co/api/v2/pokemon/${pokemonName}`);
  }
}
