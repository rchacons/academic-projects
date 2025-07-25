import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PokemonDetailsComponent } from './pokemon-details-component.component';

describe('PokemonDetailsComponent', () => {
  let component: PokemonDetailsComponent
;
  let fixture: ComponentFixture<PokemonDetailsComponent
>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PokemonDetailsComponent
     ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PokemonDetailsComponent
    );
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
