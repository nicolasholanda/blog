import {AuthService} from "../services/auth.service";

export function appInitializer(authenticationService: AuthService) {
  return () => new Promise(resolve => {
    authenticationService.refreshToken()
      .subscribe()
      .add(resolve);
  });
}
