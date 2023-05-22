import {Component, OnInit} from '@angular/core';
import {CategoriaService} from "../../services/categoria.service";

@Component({
  selector: 'app-categorias',
  templateUrl: './categorias.component.html',
  styleUrls: ['./categorias.component.scss']
})
export class CategoriasComponent implements OnInit {

  constructor(
    private categoriaService: CategoriaService
  ) {
  }

  ngOnInit(): void {
    this.categoriaService.fetchCategorias().subscribe(cs => {
      this.categorias = cs;
    })
  }

  input: string;
  categorias: string[]
  addedCategorias: string[] = []
  removedCategorias: string[] = []

  updateCategorias() {
    if (this.addedCategorias.length + this.removedCategorias.length > 0) {
      this.categoriaService.updateCategorias(this.addedCategorias, this.removedCategorias).subscribe()
    }
  }

  remove(cat: string, type: string): void {
    if (type === 'old') {
      const index = this.categorias.indexOf(cat);
      if (index >= 0) {
        this.categorias.splice(index, 1);
        this.removedCategorias.push(cat);
      }
    } else {
      const index = this.addedCategorias.indexOf(cat);
      if (index >= 0) {
        this.addedCategorias.splice(index, 1);
      }
    }

  }

  add(): void {
    const value = (this.input || '').trim();
    if (this.categorias.includes(value) || this.addedCategorias.includes(value)) return
    if (value) {
      if (this.removedCategorias.includes(value)) {
        const index = this.removedCategorias.indexOf(value);
        if (index >= 0) {
          this.removedCategorias.splice(index, 1);
        }
        this.categorias.push(value)
        return
      }
      this.addedCategorias.push(value)
    }

    this.input = ''
  }

}
