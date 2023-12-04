import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {interval, Subscription} from "rxjs";
import {URL_SERVER} from "../constants";

@Component({
  selector: 'principal-compo',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent implements OnInit {
  public  computerIds: string[] = [];

  subscription: Subscription | undefined;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.getComputers();
    this.subscription = interval(1000).subscribe(x => { this.getComputers(); });

  }

  private getComputers() {
    this.http.get<string[]>(URL_SERVER + '/computer').subscribe(data => {
      this.computerIds = data;
    });
  }
}
