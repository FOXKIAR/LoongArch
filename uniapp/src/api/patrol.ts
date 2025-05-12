export class Patrol {
    id: number;
    userId: number;
    userName: string;
    isNormal: boolean;
    comment: string;
    recordDate: Date;
}

export class PatrolPage {
    records: Array<Patrol>;
    total: number;
    size: number;
    current: number;
}

export const patrolRules = {}