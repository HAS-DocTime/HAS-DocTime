import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ChronicIllness } from '../models/chronicIllness.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ChronicIllnessService {

  constructor(private http : HttpClient) { }

  baseUrl = environment.apiUrl;

  getAllChronicIllness(){
    return this.http.get<ChronicIllness[]>(`${this.baseUrl}chronicIllness`);
  }

}
