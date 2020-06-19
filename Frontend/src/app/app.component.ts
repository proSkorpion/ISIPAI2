import { Component } from '@angular/core';
import { Router, RouterEvent, NavigationStart, NavigationEnd, NavigationCancel, NavigationError } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';  
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'BmwSalon';

  constructor(private router: Router, private SpinnerService: NgxSpinnerService) {
    this.router.events.subscribe((e : RouterEvent) => {
      this.navigationInterceptor(e);
    })
  }

  navigationInterceptor(event: RouterEvent): void {
    if (event instanceof NavigationStart) {
      this.SpinnerService.show();
    }
    if (event instanceof NavigationEnd) {
      this.SpinnerService.hide()
    }

    
    if (event instanceof NavigationCancel) {
      this.SpinnerService.hide();
    }
    if (event instanceof NavigationError) {  
      this.SpinnerService.hide();
    }
  }
}
