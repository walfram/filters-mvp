import {Criterion} from './criterion';

export interface Filter {
  id: string;
  name: string;
  criteria: Criterion[];
  active: boolean;
}
