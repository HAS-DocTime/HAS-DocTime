import { BloodGroup } from "./bloodGroup.model";
import { Gender } from "./gender.model";
import { Role } from "./role.model";

export class User{
  id? : number;
  name? : string;
  dob? : Date;
  age? : number;
  gender? : Gender;
  bloodGroup? : BloodGroup;
  contact? : string;
  height? : number;
  weight? : number;
  email? : string;
  password? : string;
  role? : Role;
}
