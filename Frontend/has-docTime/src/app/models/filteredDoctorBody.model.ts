import { Symptom } from "./symptom.model"

export type FilteredDoctorBody = {
  symptoms : Symptom[];
  timeSlotStartTime : string;
  timeSlotEndTime : string;
  description? : string;
  timeSlot? : string;
}
