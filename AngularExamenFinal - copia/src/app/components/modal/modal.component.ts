import { Component, Input, OnInit } from '@angular/core';
import { UbicacionesService } from 'src/app/services/ubicaciones.service';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnInit {

  @Input() distritoSeleccionado: string = "";
  @Input() tipoModal: string = ''; // 'comisarias', 'hospitales', 'bomberos'
  
  ubicaciones: any[] = [];

  constructor(private ubicacionesService: UbicacionesService) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    if (this.tipoModal === 'comisarias') {
      this.ubicacionesService.obtenerComisarias(this.distritoSeleccionado).subscribe(data => {
        this.ubicaciones = data;
      });
    } else if (this.tipoModal === 'hospitales') {
      this.ubicacionesService.obtenerHospitales(this.distritoSeleccionado).subscribe(data => {
        this.ubicaciones = data;
      });
    } else if (this.tipoModal === 'bomberos') {
      this.ubicacionesService.obtenerEstacionesBomberos(this.distritoSeleccionado).subscribe(data => {
        this.ubicaciones = data;
      });
    }
  }
}

