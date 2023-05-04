import { Doctor } from "./doctor.model";
import { Symptom } from "./symptom.model";
import { TimeSlot } from "./timeSlot.model";
import { User } from "./user.model";

export type Appointment = {
  id : number;
  description : string;
  user : User;
  doctor : Doctor;
  timeSlotForAppointment : TimeSlot;
  symptoms : Symptom[]
}
