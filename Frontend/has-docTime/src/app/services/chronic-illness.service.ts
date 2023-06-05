import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ChronicIllness } from '../models/chronicIllness.model';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { PagedObject } from '../models/pagedObject.model';

@Injectable({
  providedIn: 'root'
})
export class ChronicIllnessService {

  constructor(private http : HttpClient) { }

  baseUrl = environment.apiUrl;

  getAllChronicIllness(params: any): Observable<any>{

    let httpParams = new HttpParams();
    Object.keys(params).forEach((key) => {
      httpParams = httpParams.set(key, params[key]);
    });

    return this.http.get<PagedObject>(`${this.baseUrl}chronicIllness`, { params: httpParams });
  }

  getAllChronicIllnesses(){
    return this.http.get<ChronicIllness[]>(`${this.baseUrl}chronicIllness/list`);
  }

}
