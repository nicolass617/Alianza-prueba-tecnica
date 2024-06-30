import { Component, Input, OnInit } from '@angular/core';
import { ClientModel } from '../../models/client-model';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ClientService } from '../../services/client-service.service';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-new-user',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './new-user.component.html',
  styleUrl: './new-user.component.css'
})
export class NewUserComponent implements OnInit {

  @Input() clientData: ClientModel = new ClientModel();
  clientForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private clienService: ClientService,
    private activeModal: NgbActiveModal
  ) {}

  ngOnInit(): void {
    this.clientForm = this.formBuilder.group({
      businessID: ['', [Validators.required, Validators.minLength(1)]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern(/^\d*$/)]],
      startDate: [new Date().toISOString().split('T')[0], Validators.required],
      endDate: [new Date().toISOString().split('T')[0], Validators.required]
    });
  }

  closeModal(): void {
    this.activeModal.close('Closed');
  }

  saveClient(): void {
    if (this.clientForm.valid) {
      const client = this.clientForm.value as ClientModel;
      client.startDate = new Date(client.startDate);
      client.endDate = new Date(client.endDate);

      this.clienService.createNewClient(client).subscribe({
        next: (data) => {
          this.activeModal.close('saved');
          Swal.fire({
            icon: 'success',
            title: 'Transacción exitosa',
            text: 'Se guardaron los datos exitosamente',
            timer: 10000
          });
        },
        error: (error) => {
          console.log(error);
        }
      });
    }
  }

  showDateEndError(): string | null {
    const startDateControl = this.clientForm.get('startDate');
    const endDateControl = this.clientForm.get('endDate');
  
    if (startDateControl && endDateControl && startDateControl.value) {
      if (startDateControl.value >= endDateControl.value) {
        return 'La fecha final debe ser mayor a la fecha inicial';
      } 
    }
  
    return null;
  }

  showDateError(): string | null {
    const startDateControl = this.clientForm.get('startDate');
    const endDateControl = this.clientForm.get('endDate');

    const currentDate = new Date();
  
    if (startDateControl && endDateControl && startDateControl.value) {
      if (startDateControl.value >= endDateControl.value) {
        return 'La fecha de inicio debe ser menor que la fecha final';
      } else if (startDateControl.value > currentDate.toISOString().split('T')[0]) {
        return 'La fecha de inicio no puede ser mayor a la fecha actual';
      }else if (startDateControl.value < currentDate.toISOString().split('T')[0]) {
        return '';
      }
    }
  
    return null;
  }

  showFieldError(fieldName: string): boolean {
    const fieldControl = this.clientForm.get(fieldName);
    return !!fieldControl && !!fieldControl.invalid && (!!fieldControl.dirty || !!fieldControl.touched);
  }

}
