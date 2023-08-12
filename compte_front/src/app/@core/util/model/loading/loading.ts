import {Message, MessageService} from "primeng/api";
import {ResponseMessage} from "../response-message/response-message";

export class Loading {
  public loading: boolean = false;
  public spinner: string;

  constructor(spinner: string) {
    this.spinner = spinner;
  }

  error(finalIcon: string, messageService: MessageService | Message [], error: ResponseMessage): void | Message[] {
    this.spinner = finalIcon;
    this.loading = false;
    if (messageService instanceof MessageService) {
      messageService.clear();

      if (error.status != 422) {
        messageService.add({severity: 'error', summary: '', detail: error.error.message, sticky: true});
      }
    } else {
      if (error.status != 422) {
        return [{severity: 'error', summary: '', detail: error.error.message, sticky: true}]
      }


    }


  }

  success(message: string | undefined, initIcon: string, messageService: MessageService | Message[]): Message | void {
    if (message == "") {
      this.loading = false;
      this.spinner = initIcon;
      return;
    }
    this.spinner = initIcon;

    this.loading = false;


    if (messageService instanceof MessageService) {
      messageService.clear();
      messageService.add({severity: 'success', summary: 'Success', detail: message});
    } else {
      return {severity: 'success', summary: 'Success', detail: message}

    }
  }

}

