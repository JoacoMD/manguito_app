import {AfterViewInit, Component, Input, ViewChild} from '@angular/core';
import {DonacionService} from "../../services/donacion.service";
import {DonacionManguito} from "../../models/donacion.model";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-lista-manguitos',
  templateUrl: './lista-manguitos.component.html',
  styleUrls: ['./lista-manguitos.component.scss']
})
export class ListaManguitosComponent implements AfterViewInit {

  dataSource: MatTableDataSource<DonacionManguito> = new MatTableDataSource<DonacionManguito>([])
  displayedColumns: string[] = ['nombre', 'contacto', 'cantidad', 'monto', 'mensaje', 'fecha']

  loading = true

  @ViewChild(MatPaginator) paginator: MatPaginator
  @ViewChild(MatSort) sort: MatSort

  constructor(
    private donacionService: DonacionService
  ) {
    this.donacionService.loadingDonaciones.subscribe(loading => {
      this.loading = loading
    })
    this.donacionService.manguitosRecibidos.subscribe(manguitos => {
      this.dataSource.data = manguitos?.map(m => ({...m, fecha: new Date(m.fecha)})) ?? []
    })
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

}
