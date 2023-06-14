import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Country } from '../models/country.model';

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  countries: Country[] = [];

  constructor(private http: HttpClient) { }

    async getAllCountries() : Promise<Country[]> {
    const data = await this.http.get<any[]>(`https://restcountries.com/v3.1/all?fields=name,idd,flag`).toPromise();
      data?.forEach((country) => {
        if(country.idd){
          const countryCode = country.idd.root;
          const flag = country.flag;
          const suffixes = (country.idd.suffixes as any[]).map( suffix => countryCode + suffix);
          suffixes.forEach( suffix => {
            this.countries.push({
              code : suffix,
              name : country.name.common + flag 
            });
          });

          this.countries.sort((a, b) => a.name.localeCompare(b.name));
        }
      });

    return this.countries;
  }
}
