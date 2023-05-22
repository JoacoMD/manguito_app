import {Component, OnInit, ViewChild} from '@angular/core';
import {Emprendimiento} from "../models/emprendimiento.model";
import {EmprendimientoService} from "../services/emprendimiento.service";
import {NgForm} from "@angular/forms";
import {Page} from "../models/page.model";
import {finalize} from "rxjs";
import {CategoriaService} from "../services/categoria.service";
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-emprendimientos',
  templateUrl: './emprendimientos.component.html',
  styleUrls: ['./emprendimientos.component.css']
})
export class EmprendimientosComponent implements OnInit {

  constructor(
    private emprendimientoService: EmprendimientoService,
    private categoriaService: CategoriaService
  ) {
  }

  loading: boolean = false
  @ViewChild("searchForm") searchForm: NgForm;

  pagina: Page<Emprendimiento>;
  currentPage: number = 0;
  emprendimientos: Emprendimiento[] = []
  categorias: string[] = []
  currentFilter: {nombre?: string, categorias?: string[]} = {}

  ngOnInit() {
    this.search({page: this.currentPage})
    this.categoriaService.fetchCategorias().subscribe((cats) => {
      this.categorias = cats;
    })
  }

  search(filter) {
    this.loading = true
    this.emprendimientoService.searchEmprendimientos(filter)
      .pipe(finalize(() => {
        this.loading = false
      }))
      .subscribe(page => {
        this.pagina = page
        this.loading = false
      })
  }

  onSearch(form: NgForm) {
    if (form.invalid) return
    this.currentFilter = form.value
    this.search({...form.value, page: this.currentPage})
  }

  handlePageEvent(event: PageEvent) {
    this.search({...this.currentFilter, page: event.pageIndex})
  }

}
