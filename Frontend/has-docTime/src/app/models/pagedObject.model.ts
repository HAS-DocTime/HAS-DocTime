import { Admin } from "./admin.model"
import { Appointment } from "./appointment.model"
import { ChronicIllness } from "./chronicIllness.model"
import { Department } from "./department.model"
import { DiseaseCount } from "./diseaseCount.model"
import { Doctor } from "./doctor.model"
import { PastAppointment } from "./pastAppointment.model"
import { PatientChronicIllness } from "./patientChronicIllness.model"
import { Symptom } from "./symptom.model"
import { User } from "./user.model"

export type PagedObject = {
  content : User[] | Doctor[] | Admin[] | Appointment[] | PatientChronicIllness[] | ChronicIllness[] | Department[] | DiseaseCount[] | PastAppointment[] |  Symptom[] ;
  pageable : {
    sort: {
      empty : boolean;
      sorted : boolean;
      unsorted : boolean;
    }
    offset : number;
    pageNumber : number;
    pageSize : number;
    paged : boolean;
    unpaged : boolean;
  };
  totalPages : number;
  totalElements : number;
  last : boolean;
  size : number;
  number : number;
  sort : {
    empty : boolean;
      sorted : boolean;
      unsorted : boolean;
  };
  numberOfElements : number;
  first : boolean;
  empty : boolean;
}
