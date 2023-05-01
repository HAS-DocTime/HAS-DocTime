import { BloodGroup } from "./bloodGroup.model";
import { ChronicIllness } from "./chronicIllness.model";
import { Gender } from "./gender.model";
import { Role } from "./role.model";
import { User } from "./user.model";

export type Doctor = {
  id? : number;
  user : {
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
    patientChronicIllness? : ChronicIllness;
  };
  qualification : string;
  casesSolved : number;
  available : boolean;
  department : {
    id : number;
  }
}
