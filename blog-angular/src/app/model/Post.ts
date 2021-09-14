import {User} from "./User";

export interface Post {
  title: string;
  content: string;
  updateDate: string | null;
  creationDate: string | null;
  user: User | null;
}
