import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ComputerDetailsComponent } from './computer-details/computer-details.component';
import {PrincipalComponent} from "./principal/principal.component";
import {MalwareComponent} from "./malware/malware.component";

const routes: Routes = [
  { path: '', component: PrincipalComponent },
  { path: 'details/:id', component: ComputerDetailsComponent },
  { path: 'malware', component: MalwareComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
