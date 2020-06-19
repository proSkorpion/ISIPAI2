import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainSiteComponent } from './main-site/main-site.component';
import { ContactSiteComponent } from './contact-site/contact-site.component';
import {LoginComponent} from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DetailsCarComponent } from './details-car/details-car.component';
import { MeetingComponent } from './meeting/meeting.component';
import { DetailsClientComponent } from './details-client/details-client.component';
import { ConclusionComponent } from './conclusion/conclusion.component';
import { DataResolverService } from './shared/data-resolver.service';
import { TypeResolverService } from './shared/type-resolver.service';
import { AuthenticationGuard } from './shared/authentication.guard';
import { isNotLoggedGuard } from './shared/isNotLogged.guard';


const routes: Routes = [
  {
    component: MainSiteComponent,
    path: 'mainSite',
    resolve: {
      productList: DataResolverService,
      typeList: TypeResolverService
    }
  },
  {
    component: ContactSiteComponent,
    path: 'contact'
  },
  {
    path: '',
    redirectTo: 'mainSite',
    pathMatch: 'full'
  },
  {
    component: LoginComponent,
    path: 'login',
    canActivate: [AuthenticationGuard]
  },
  {
    component: RegisterComponent,
    path: 'register',
    canActivate: [AuthenticationGuard]
  },
  {
    component: DetailsCarComponent,
    path: 'detailsCars',
    resolve: {
      productList: DataResolverService
    }
  },
  {
    component: MeetingComponent,
    path: 'meeting',
    resolve: {
      productList: DataResolverService
    }
  },
  {
    component: DetailsClientComponent,
    path: 'detailsClient',
    canActivate: [isNotLoggedGuard]
  },
  {
    component: ConclusionComponent,
    path: 'conclusion'
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
