import { Doctor } from "./doctor.model";
import { User } from "./user.model";
import { TimeSlot } from "./timeSlot.model";

export type MedicalHistory = {
  id? : number;
  disease? : string;
  medicine? : string;
  user?: User;
  doctor?: Doctor;
  timeSlotForAppointmentData?: TimeSlot;
}
