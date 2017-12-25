import { Injectable } from '@angular/core';
import {
  CanActivate, Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot
} from '@angular/router';
import { AuthorizationService } from './authorization.service';

@Injectable()
export class RoleGuardService implements CanActivate {
    constructor(private authService: AuthorizationService, private router: Router) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        const url: string = state.url;
        const allowedRole: string = route.data.allowedRole;

        return this.checkRole(url, allowedRole);
    }

    checkRole(url: string, allowedRole: string): boolean {
        if (this.authService.hasRole(allowedRole)) { return true; }

        // Store the attempted URL for redirecting
        this.authService.redirectUrl = url;

        // Navigate to the login page with extras
        this.router.navigate(['/']);
        return false;
    }
}
