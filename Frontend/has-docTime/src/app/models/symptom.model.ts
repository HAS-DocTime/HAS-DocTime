import { Appointment } from "./appointment.model";
import { Department } from "./department.model";

export type Symptom = {
  id : number;
  name? : string;
  departments? : Department[];
  appointments? : Appointment[];
  caseCount? : number;
}
