import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {

  private apiUrl = 'http://localhost:8080/customers/';

  constructor(private http: HttpClient){}


  findByPhone(phone : string): Observable<Customer> {
    return this.http.get<Customer>(this.apiUrl + `${phone}`);
  }

  findOrCreate(customer : customerCreate): Observable<Customer> {
    
    return this.http.post<Customer>(this.apiUrl, customer);

  }


  
}
