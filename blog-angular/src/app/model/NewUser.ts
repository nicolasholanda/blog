import {User} from "./User";

export interface NewUser extends User {
  password: string;
}
