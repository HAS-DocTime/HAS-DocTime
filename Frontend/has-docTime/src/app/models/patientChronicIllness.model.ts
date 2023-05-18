import { ChronicIllness } from "./chronicIllness.model";
import { User } from "./user.model";
​
export type PatientChronicIllness = {
  id : {
    patientId: number;
    chronicIllnessId: number;
  };
  chronicIllness: ChronicIllness;
  user?: User;
  yearsOfIllness : number;
}