import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private url = "http://localhost:8080/api/users";

  constructor(private http: HttpClient) { }

  getAllClients(): Observable<any> {
    return this.http.get(`${this.url}/allUsers`);
  }

  createNewClient(client: any): Observable<any> {
    return this.http.post(`${this.url}/save`, client);
  }

  searchClientBySharedKey(sharedKey: string): Observable<any> {
    return this.http.get(`${this.url}/bySharedKey?sharedKey=${sharedKey}`);
  }

}
