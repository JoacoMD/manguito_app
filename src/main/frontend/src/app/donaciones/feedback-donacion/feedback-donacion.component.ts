import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DonacionService} from "../../services/donacion.service";
import {finalize} from "rxjs";

@Component({
  selector: 'app-feedback-donacion',
  templateUrl: './feedback-donacion.component.html',
  styleUrls: ['./feedback-donacion.component.scss']
})
export class FeedbackDonacionComponent implements OnInit{

  constructor(
    private route: ActivatedRoute,
    private donacionService: DonacionService,
    private router: Router
  ) {}

  donacion: any
  estado: string
  loading: boolean = false

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      if (params['external_reference']) {
        this.loading = true
        this.donacionService.getDonacionByExternalReference(params['external_reference'])
          .pipe(finalize(() => { this.loading = false }))
          .subscribe((data) => {
            this.donacion = data
            this.estado = data.estado
        })
      } else {
        this.router.navigate(['/'])
      }
    })
  }

}
