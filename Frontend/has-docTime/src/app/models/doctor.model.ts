import { User } from "./user.model";

export type Doctor = {
  id? : number;
  user : {
    id : number;
  };
  qualification : string;
  casesSolved : number;
  available : boolean;
  department : {
    id : number;
  }
}
