import { BloodGroup } from "./bloodGroup.model";
import { ChronicIllness } from "./chronicIllness.model";
import { Gender } from "./gender.model";
import { Role } from "./role.model";

export type User = {
  id? : number;
  name : string;
  dob : Date;
  age? : number;
  gender : Gender;
  bloodGroup : BloodGroup;
  contact : string;
  height? : number;
  weight? : number;
  email : string;
  password : string;
  role : Role;
  patientChronicIllness : ChronicIllness;
}
