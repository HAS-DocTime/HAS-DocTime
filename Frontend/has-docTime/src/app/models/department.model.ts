import { Symptom } from "./symptom.model";

export type Department = {
  id? : number;
  name? : string;
  building? : string;
  timeDuration? : string;
  description? : string;
  departmentImage? : string;
  symptoms? : Symptom[];
}
