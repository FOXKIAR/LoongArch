export const serverUrl = 'http://localhost:8080';

export interface Result {
    msg: string;
    data: any;
}

export class Page<T> {
    records: Array<T>;
    total: number;
    size: number;
    current: number;
}