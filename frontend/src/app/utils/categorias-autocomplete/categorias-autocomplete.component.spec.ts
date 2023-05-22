import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriasAutocompleteComponent } from './categorias-autocomplete.component';

describe('CategoriasAutocompleteComponent', () => {
  let component: CategoriasAutocompleteComponent;
  let fixture: ComponentFixture<CategoriasAutocompleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CategoriasAutocompleteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoriasAutocompleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
