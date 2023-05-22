import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmprendimientosMasDonadosComponent } from './emprendimientos-mas-donados.component';

describe('EmprendimientosMasDonadosComponent', () => {
  let component: EmprendimientosMasDonadosComponent;
  let fixture: ComponentFixture<EmprendimientosMasDonadosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmprendimientosMasDonadosComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmprendimientosMasDonadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
