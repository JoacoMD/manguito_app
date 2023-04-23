import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  ViewChild
} from '@angular/core';
import {MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {CategoriaService} from "../../services/categoria.service";
import {map, Observable, startWith} from "rxjs";
import {FormControl} from "@angular/forms";
import {ENTER} from "@angular/cdk/keycodes";
import {MatChipInputEvent} from "@angular/material/chips";

@Component({
  selector: 'app-categorias-autocomplete',
  templateUrl: './categorias-autocomplete.component.html',
  styleUrls: ['./categorias-autocomplete.component.scss']
})
export class CategoriasAutocompleteComponent implements OnInit, OnChanges {

  constructor(
    private categoriaService: CategoriaService
  ) {
    this.filteredCategories = this.catCtrl.valueChanges.pipe(
      startWith(null),
      map((cat: string | null) => (cat ? this._filter(cat) : this._filter(''))),
    );
  }

  ngOnChanges(changes: SimpleChanges) {
    this.categories = this.categorias;
  }
  separatorKeysCodes: number[] = [ENTER];
  catCtrl = new FormControl('');
  filteredCategories: Observable<string[]>;
  categories: string[] = [];
  allCategories: string[] = [];

  @Output() onChange = new EventEmitter<string[]>()

  @Input() categorias: string[]
  @Input() disableAdd: boolean
  @Input() error: boolean

  @ViewChild('catInput') catInput: ElementRef<HTMLInputElement>;

  ngOnInit() {
    this.categoriaService.fetchCategorias().subscribe(cats => {
      this.allCategories = cats
    })
  }

  add(event: MatChipInputEvent): void {
    if (this.disableAdd) return
    const value = (event.value || '').trim();

    // Add our fruit
    if (value) {
      this.categorias.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();

    this.catCtrl.setValue(null);
  }

  remove(fruit: string): void {
    const index = this.categories.indexOf(fruit);

    if (index >= 0) {
      this.categories.splice(index, 1);
      this.onChange.emit(this.categories)
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.catInput.nativeElement.value = '';
    this.catCtrl.setValue(null);
    if (this.categories.includes(event.option.viewValue)) return
    this.categories.push(event.option.viewValue);
    this.onChange.emit(this.categories)
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.allCategories.filter(cat => cat.toLowerCase().includes(filterValue));
  }

}
