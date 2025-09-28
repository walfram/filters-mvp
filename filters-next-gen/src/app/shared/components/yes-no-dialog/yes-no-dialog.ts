import {Component, inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle} from '@angular/material/dialog';
import {MatButton} from '@angular/material/button';

export interface YesNoDialogOptions {
  title: string;
  message: string;
}

@Component({
  selector: 'app-yes-no-dialog',
  imports: [
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatButton,
    MatDialogClose
  ],
  templateUrl: './yes-no-dialog.html',
  styleUrl: './yes-no-dialog.css'
})
export class YesNoDialog {

  protected readonly data = inject(MAT_DIALOG_DATA);

}
