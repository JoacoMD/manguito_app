import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Injectable} from "@angular/core";
import {map, Observable} from "rxjs";
import {AuthService} from "../services/auth.service";

@Injectable({providedIn: 'root'})
export class LoggedGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.authService.user.pipe(map(user => {
      const isAuth = !!user
      if (!isAuth) {
        return true
      }
      return this.router.createUrlTree(['/'])
    }));
  }

}
