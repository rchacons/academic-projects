import { Component } from '@angular/core';
import { Pokemon } from '../pokemon';
import { PokeService } from '../poke-service.service';
import { PokemonDataService } from '../pokemon-data.service';

@Component({
  selector: 'app-my-component',
  templateUrl: './my-component.component.html',
  styleUrls: ['./my-component.component.css']
})

export class MyComponent {

  id: string = "";

  // Attributes for q1-12
  pokemonList: Pokemon[];
  selectedPokemon?: Pokemon;
  searchText = "";

  // Attributes for q13-14
  searchTextBis = "";
  selectedPokemonBis?: Pokemon;  // Pokemon chosen from the list
  pokemonToDisplay?: Pokemon;    // Pokemon passed to the pokemon component to display its information

  constructor(private pokeService: PokeService, private pokemonDataService: PokemonDataService
  ) {

    this.pokemonList = [
      new Pokemon(1, "Bulbasaur"),
      new Pokemon(4, "Charmander"),
      new Pokemon(7, "Squirtle"),
      new Pokemon(10, "Caterpie"),
      new Pokemon(16, "Pidgey")
    ]


    // Populating the list from the API
    pokeService.getPokemonList().subscribe(
      (data: any) => {
        this.pokemonList = data.results;
      },
      (error) => console.log(error)
    );

  }

  onButtonClick(pokemon?: Pokemon) {
    console.log("id : " + pokemon?.id + ", name : " + pokemon?.name)
  }

  searchPokemon() {
    if (this.selectedPokemonBis) {

      this.pokemonDataService.setSelectedPokemon(this.selectedPokemonBis.name);
    }

  }

}





