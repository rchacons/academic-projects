export class Person {
    id ?: number;
    name ?: string;
    type ?: TypePerson;
}

export enum TypePerson {
    USER = "USER",
    SUPPORT_MEMBER = "SUPPORT_MEMBER"
}