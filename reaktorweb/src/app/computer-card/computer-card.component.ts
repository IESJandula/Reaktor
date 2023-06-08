import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-computer-card',
  templateUrl: './computer-card.component.html',
  styleUrls: ['./computer-card.component.css']
})
export class ComputerCardComponent {
  @Input() id: string = "";
  @Input() logoUrl: string = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/87/Windows_logo_-_2021.svg/512px-Windows_logo_-_2021.svg.png";
  @Input() malwareCount: number = 0;
  @Input() isAdmin: boolean = false;
  @Input() location: string = '';
  @Input() responsable: string = '';
  @Input() computerOn: boolean = false;

  constructor(private router: Router) { }

  goToDetails(id: string) {
    this.router.navigate(['/details', id]);
  }

  getStyle() {
    if (this.computerOn)
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
