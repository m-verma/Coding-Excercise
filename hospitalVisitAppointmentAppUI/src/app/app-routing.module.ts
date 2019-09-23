import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SearchAppointmentComponent } from './search-appointment/search-appointment.component';
import { ScheduleAppointmentComponent } from './schedule-appointment/schedule-appointment.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  { path:'', component: HomeComponent},
  { path:'scheduleAppointment', component: ScheduleAppointmentComponent},
  { path:'searchAppointment', component: SearchAppointmentComponent},
  { path:'**', redirectTo:''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
