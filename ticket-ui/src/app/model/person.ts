export interface Person {
    id ?: number;
    name ?: string;
    type ?: typePerson;
}

export enum typePerson {
    USER = "User",
    SUPPORT_MEMBER = "Support"
}