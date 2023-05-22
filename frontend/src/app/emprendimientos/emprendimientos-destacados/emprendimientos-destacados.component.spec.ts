import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmprendimientosDestacadosComponent } from './emprendimientos-destacados.component';

describe('EmprendimientosDestacadosComponent', () => {
  let component: EmprendimientosDestacadosComponent;
  let fixture: ComponentFixture<EmprendimientosDestacadosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmprendimientosDestacadosComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmprendimientosDestacadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
