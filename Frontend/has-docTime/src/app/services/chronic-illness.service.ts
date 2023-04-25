import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ChronicIllness } from '../models/chronicIllness.model';

@Injectable({
  providedIn: 'root'
})
export class ChronicIllnessService {

  constructor(private http : HttpClient) { }

  baseUrl = "http://localhost:8080/";

  getAllChronicIllness(){
    return this.http.get<ChronicIllness[]>(`${this.baseUrl}chronicIllness`);
  }

}
