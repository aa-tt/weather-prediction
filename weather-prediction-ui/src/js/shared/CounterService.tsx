import { Subject } from 'rxjs';

const subject = new Subject<string | null>();

export const counterService = {
  sendMessage: message => subject.next(message),
  clearMessages: () => subject.next(null),
  onMessage: () => subject.asObservable()
};