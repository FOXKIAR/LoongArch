export class Patrol {
    recordDate: Date;
    userId: number;
    userName: string;
    isNormal: boolean;
    comment: string;
    startDate: Date;
    endDate: Date;
}

export class PatrolPage {
    records: Array<Patrol>;
    total: number;
    size: number;
    current: number;
}

export const patrolRules = {}