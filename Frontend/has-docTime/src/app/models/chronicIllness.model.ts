import { PatientChronicIllness } from "./patientChronicIllness.model";

export type ChronicIllness = {
  id : number;
  name : string;
  patientChronicIllnesses : PatientChronicIllness[];
}
