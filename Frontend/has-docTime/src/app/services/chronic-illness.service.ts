import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ChronicIllness } from '../models/chronicIllness.model';

@Injectable({
  providedIn: 'root'
})
export class ChronicIllnessService {

  constructor(private http : HttpClient) { }

  baseUrl = `http://192.1.200.29:8080/`;

  getAllChronicIllness(){
    return this.http.get<ChronicIllness[]>(`${this.baseUrl}chronicIllness`);
  }

}
