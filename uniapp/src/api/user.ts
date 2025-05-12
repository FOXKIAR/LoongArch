export class User {
    id: number;
    name: string;
    account: string;
    password: string;
    email: string;
    phone: string;
    permission: number;
}

export class UserPage {
    records: Array<User>;
    total: number;
    size: number;
    current: number;
}

export enum Permission {
    GET = 0b0001,
    PUT = 0b0010,
    DELETE = 0b0100,
    POST = 0b1000,
}

export const userRules = {
    name: {
        rules: [
            {required: true, errorMessage: "不能为空"}
        ]
    },
    account: {
        rules: [
            {required: true, errorMessage: "不能为空"}, 
            {minLength: 8, maxLength: 16, errorMessage: "长度应该在8~16个字符"}
        ]
    },
    password: {
        rules: [
            {required: true, errorMessage: "不能为空"}, 
            {minLength: 8, maxLength: 16, errorMessage: "长度应该在8~16个字符"}
        ]
    },
    email: {
        rules: [
            {format: 'email', errorMessage: "不是可用的邮箱格式"}
        ]
    },
    phone: {
        rules: [
            {pattern: new RegExp('^1[3-9]\\d{9}$'), errorMessage: "不是可用的手机号格式"}
        ]
    }
};