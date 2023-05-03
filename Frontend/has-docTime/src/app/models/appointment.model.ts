import { Doctor } from "./doctor.model";
import { User } from "./user.model";

export type Appointment = {
  id? : number;
  description : string;
  user : User;
  doctor : Doctor;
}
