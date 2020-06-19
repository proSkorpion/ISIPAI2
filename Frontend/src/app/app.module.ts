import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { MainSiteComponent } from './main-site/main-site.component';
import { ContactSiteComponent } from './contact-site/contact-site.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { CompareValidatorDirective } from './shared/compare-validator.directive';
import { ProductItemComponent } from './product-item/product-item.component';
import { DetailsCarComponent } from './details-car/details-car.component';
import { MeetingComponent } from './meeting/meeting.component';
import { DetailsClientComponent } from './details-client/details-client.component';
import { ConclusionComponent } from './conclusion/conclusion.component';
import { FiltersComponent } from './filters/filters.component';
import { DataResolverService } from './shared/data-resolver.service';
import { NgxSpinnerModule } from 'ngx-spinner';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    MainSiteComponent,
    ContactSiteComponent,
    LoginComponent,
    RegisterComponent,
    CompareValidatorDirective,
    FiltersComponent,
    ProductItemComponent,
    DetailsCarComponent,
    MeetingComponent,
    DetailsClientComponent,
    ConclusionComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule,
    NgxSpinnerModule
  ],
  providers: [HttpClientModule, DataResolverService],
  bootstrap: [AppComponent]
})
export class AppModule { }
