import { Component, Input, OnInit } from '@angular/core';
import {Motherboard, SimpleComputerDTO} from '../computer-dto';
import {HttpClient} from '@angular/common/http';
import { Router } from '@angular/router';
import { URL_SERVER } from '../constants';

@Component({
  selector: 'app-computer-card',
  templateUrl: './computer-card.component.html',
  styleUrls: ['./computer-card.component.css']
})
export class ComputerCardComponent implements OnInit{
  @Input() computerId: string = "";

  public simpleComputer: SimpleComputerDTO = {
    id: "notFound",
    malwareCount: 0,
    location: "",
    responsable: "",
    computerOn: false,
    isAdmin: false,
    computerNumber: ""
  };

  public logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/87/Windows_logo_-_2021.svg/512px-Windows_logo_-_2021.svg.png"


  constructor(
    private router: Router,
    private http: HttpClient
  ) { }


  ngOnInit(): void {
    this.http.get<SimpleComputerDTO>(URL_SERVER + '/computer/' + this.computerId).subscribe(data => {
      this.simpleComputer = data;
    });
  }

  goToDetails() {
    this.router.navigate(['/details', this.computerId]);
  }

  getStyle() {
    if (this.simpleComputer.computerOn)
    {
      return {
        'background': 'rgb(93, 255, 128)'
      }
    }
    else
    {
      return {
        'background': 'rgb(255, 125, 125)'
      }
    }
  }
}
