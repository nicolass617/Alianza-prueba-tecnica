import { Component, OnInit } from '@angular/core';
import { ClientModel } from '../../models/client-model';
import { ClientService } from '../../services/client-service.service';
import { CommonModule } from '@angular/common';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { NewUserComponent } from '../new-user/new-user.component';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-clients-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './clients-list.component.html',
  styleUrl: './clients-list.component.css'
})
export class ClientsListComponent implements OnInit {

  sharedKey!: string;
  clientList: ClientModel[] = [];

  constructor(
    private clientService: ClientService,
    private modal: NgbModal
  ) {}

  ngOnInit(): void {
    this.getAllClients();
  }

  getAllClients(): void {
    this.clientService.getAllClients().subscribe({
      next: (response) => {
        this.clientList = response
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  searchBySharedKey(): void {
    if (this.sharedKey) {
      this.clientService.searchClientBySharedKey(this.sharedKey).subscribe({
        next: (data) => {
          this.clientList = [data];
        },
        error: (error) => {
          console.log(error);
        }
      });
    } else {
      this.getAllClients();
    }
    
  }

  openNewClientModal(): void {
    const refModal = this.modal.open(NewUserComponent, { centered: true });
    const newClient: ClientModel = {
      businessID: '',
      dateAdded: new Date(),
      email: '',
      endDate: new Date(),
      phone: '',
      sharedKey: '',
      startDate: new Date()
    }

    refModal.componentInstance.clientData = newClient;

    refModal.result.then((result) => {
      if (result === 'saved') {
        this.getAllClients();
      }
    }).catch((error) => {
      console.log(error);
    });
  }

  exportDataClients(): void {
    const data = this.clientList.map(client => {
      return {
        'Shared Key': client.sharedKey,
        'Business ID': client.businessID,
        'E-mail': client.email,
        'Phone': client.phone,
        'Date Added': client.dateAdded,
        'Start Date': client.startDate,
        'End Date': client.endDate
      };
    });

    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(data);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Clients');
    XLSX.writeFile(wb, 'clients.xlsx');
  }

}
