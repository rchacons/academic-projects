import { AfterContentChecked, Component, Input, OnInit, ɵɵsetComponentScope } from '@angular/core';
import { Pokemon } from '../pokemon';
import { PokeService } from '../poke-service.service';
import { map } from 'rxjs/operators';
import { PokemonDataService } from '../pokemon-data.service';




@Component({
  selector: 'app-pokemon-details-component',
  templateUrl: './pokemon-details-component.component.html',
  styleUrls: ['./pokemon-details-component.component.css']
})
export class PokemonDetailsComponent implements OnInit {

  //@Input() pokemon? : Pokemon;
  pokemon?: Pokemon;
  pokemonName: string = '';

  constructor(private pokeService: PokeService, private pokemonDataService: PokemonDataService) { }

  ngOnInit() {
    /**
     * We subscribe to the _selectedPokemonId subject. Whenever the 'next' method is called
     * on this subject from any other component, the subscriber (this component) will receive the new value.
     */
    this.pokemonDataService.getSelectedPokemonId().subscribe(name => {
      this.pokemonName = name;
      console.log(this.pokemonName)
      this.searchPokemon()
    })

    }


  searchPokemon() {
    
    if(this.pokemonName){
      
      // We get the data and update the pokemon to display
      this.pokeService.getPokemonDetails(this.pokemonName)
        .subscribe((pokemonData: any) => {
          console.log(pokemonData)
          this.pokemon = new Pokemon
            (pokemonData.id,
              pokemonData.name,
              pokemonData.types.map(
                (type: any) => type.type.name
              ),
              pokemonData.stats.map(
                (stat: any) => {
                  return {
                    stat: stat.stat.name,
                    baseStat: stat.base_stat
                  };
                }
              ),
              `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonData.id}.png`
            );
        });
    }
    }


}
