import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ClientService } from './client-service.service';

describe('ClientService', () => {
  let service: ClientService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ClientService]
    });
    service = TestBed.inject(ClientService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all clients', () => {
    const dummyClients = [{ name: 'John' }, { name: 'Doe' }];

    service.getAllClients().subscribe(clients => {
      expect(clients.length).toBe(2);
      expect(clients).toEqual(dummyClients);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/users/allUsers');
    expect(req.request.method).toBe('GET');
    req.flush(dummyClients);
  });

  it('should create a new client', () => {
    const newClient = { name: 'John' };

    service.createNewClient(newClient).subscribe(client => {
      expect(client).toEqual(newClient);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/users/save');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newClient);
    req.flush(newClient);
  });

  it('should search client by shared key', () => {
    const sharedKey = '12345';
    const dummyClient = { name: 'John', sharedKey: '12345' };

    service.searchClientBySharedKey(sharedKey).subscribe(client => {
      expect(client).toEqual(dummyClient);
    });

    const req = httpMock.expectOne(`http://localhost:8080/api/users/bySharedKey?sharedKey=${sharedKey}`);
    expect(req.request.method).toBe('GET');
    req.flush(dummyClient);
  });
});
