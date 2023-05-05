import { BloodGroup } from "./bloodGroup.model";
import { ChronicIllness } from "./chronicIllness.model";
import { Department } from "./department.model";
import { Gender } from "./gender.model";
import { Role } from "./role.model";
import { User } from "./user.model";

export type Doctor = {
  id? : number;
  user : User;
  qualification : string;
  casesSolved : number;
  available : boolean;
  department : Department;
}
