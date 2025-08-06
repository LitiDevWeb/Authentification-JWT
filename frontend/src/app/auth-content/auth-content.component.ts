import { Component, OnInit } from '@angular/core';
import { AxiosService } from '../axios.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-auth-content',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './auth-content.component.html',
  styleUrl: './auth-content.component.css'
})
export class AuthContentComponent implements OnInit{
  data : string[] = [];

  constructor(private axiosService: AxiosService){  }

    ngOnInit() : void {
      this.axiosService.request(
        "GET",
        "/messages",
        null
      ).then(
        (response) => this.data = response.data
      )
    }

}
