import { Doctor } from "./doctor.model";
import { User } from "./user.model";
import { TimeSlot } from "./timeSlot.model";

export type PastAppointment = {
  id? : number;
  disease? : string;
  medicine? : string;
  user?: User;
  doctor?: Doctor;
  timeSlotForAppointmentData?: TimeSlot;
  symptoms?: string;
}
